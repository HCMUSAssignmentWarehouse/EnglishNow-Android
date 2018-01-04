package com.iceteaviet.englishnow.ui.main.view;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.data.model.others.QueuedMedia;
import com.iceteaviet.englishnow.databinding.DialogComposeStatusBinding;
import com.iceteaviet.englishnow.ui.base.BaseDialog;
import com.iceteaviet.englishnow.ui.main.ComposerNavigator;
import com.iceteaviet.englishnow.ui.main.viewmodel.ComposerViewModel;
import com.iceteaviet.englishnow.utils.CountUpDownLatch;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Genius Doan on 04/01/2018.
 */

public class StatusComposerDialog extends BaseDialog implements ComposerNavigator {
    public static final String TAG = StatusComposerDialog.class.getSimpleName();

    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int PERMISSIONS_REQUEST_CAMERA = 2;

    @Inject
    ComposerViewModel composerViewModel;

    DialogComposeStatusBinding composerBinding;


    Button postButton;
    EditText textEditor;
    TextView charCountTextView;
    ImageButton pickButton;
    ImageButton saveDraftButton;

    private Uri photoUploadUri;

    private StatusComposerDialogListener mListener;

    public StatusComposerDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static StatusComposerDialog newInstance() {
        StatusComposerDialog fragment = new StatusComposerDialog();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    private static Editable removeUrlFromEditable(Editable editable, @Nullable URLSpan urlSpan) {
        if (urlSpan == null) {
            return editable;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(editable);
        int start = builder.getSpanStart(urlSpan);
        int end = builder.getSpanEnd(urlSpan);
        if (start != -1 && end != -1) {
            builder.delete(start, end);
        }
        return builder;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }

    public void setOnPostedListener(StatusComposerDialogListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CountUpDownLatch.getInstance().reset();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        composerBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_compose_status, container, false);
        View convertView = composerBinding.getRoot();

        AndroidInjection.inject(this);

        composerBinding.setViewModel(composerViewModel);
        composerViewModel.setNavigator(this);
        composerViewModel.setContext(getActivity());
        composerViewModel.setContentResolver(getActivity().getContentResolver());

        return convertView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get view
        postButton = composerBinding.btnPost;
        charCountTextView = composerBinding.tvCountChar;
        textEditor = composerBinding.editor;
        pickButton = composerBinding.composePhotoPick;
        saveDraftButton = composerBinding.composeSaveDraft;

        setCharsCounter(textEditor);
        //
        textEditor.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onResume() {
        //Get existing layout params of window
        ViewGroup.LayoutParams layoutParams = getDialog().getWindow().getAttributes();

        //Assign window properties to layout params width and height
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        //Set the layout params back to the dialog
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) layoutParams);

        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void postStatus() {
        String content = textEditor.getText().toString();
        if (!composerViewModel.isStatusValid(content)) {

        } else {
            composerViewModel.doPostStatus(content);
            mListener.onPosted(content);
            dismiss();
        }
    }

    @Override
    public void openPickChooseDialog() {
        final int CHOICE_TAKE = 0;
        final int CHOICE_PICK = 1;
        CharSequence[] choices = new CharSequence[2];
        choices[CHOICE_TAKE] = getString(R.string.action_photo_take);
        choices[CHOICE_PICK] = getString(R.string.action_photo_pick);
        DialogInterface.OnClickListener listener = (dialog, which) -> {
            switch (which) {
                case CHOICE_TAKE: {
                    initiateCameraApp();
                    break;
                }
                case CHOICE_PICK: {
                    onMediaPick();
                    break;
                }
            }
        };
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setItems(choices, listener)
                .create();
        dialog.show();
    }

    private boolean saveDraft() {
        /*
        String contentWarning = null;
        if (statusHideText) {
            contentWarning = contentWarningEditor.getText().toString();
        }
        Editable textToSave = textEditor.getEditableText();
        // Discard any upload URLs embedded in the text because they'll be re-uploaded when
         // the draft is loaded and replaced with new URLs.
        if (mediaQueued != null) {
            for (QueuedMedia item : mediaQueued) {
                textToSave = removeUrlFromEditable(textToSave, item.uploadUrl);
            }
        }
        boolean didSaveSuccessfully = saveTheToot(textToSave.toString(), contentWarning);
        if (didSaveSuccessfully) {
            Toast.makeText(ComposeActivity.this, R.string.action_save_one_toot, Toast.LENGTH_SHORT)
                    .show();
        }
        return didSaveSuccessfully;
        */
        return true;
    }

    private void initiateCameraApp() {
        if (!getBaseActivity().hasPermission(Manifest.permission.CAMERA)) {
            getBaseActivity().requestPermissionsSafely(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = composerViewModel.createNewImageFile();
                } catch (IOException ex) {
                    displayTransientError(R.string.error_media_upload_opening);
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    photoUploadUri = FileProvider.getUriForFile(getActivity(),
                            "com.iceteaviet.englishnow.fileprovider",
                            photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUploadUri);
                    startActivityForResult(intent, ComposerViewModel.MEDIA_TAKE_PHOTO_RESULT);
                }
            }
        }
    }

    private void onMediaPick() {
        if (!getBaseActivity().hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            getBaseActivity().requestPermissionsSafely(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            initiateMediaPicking();
        }
    }

    private void initiateMediaPicking() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = new String[]{"image/*", "video/*"};
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(intent, "Select media"), ComposerViewModel.MEDIA_PICK_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ComposerViewModel.MEDIA_PICK_RESULT && data != null) {
            composerViewModel.pickMedia(data.getData());
        } else if (resultCode == Activity.RESULT_OK && requestCode == ComposerViewModel.MEDIA_TAKE_PHOTO_RESULT) {
            composerViewModel.pickMedia(photoUploadUri);
        }
    }

    @Override
    public void addMediaPreview(QueuedMedia item, Bitmap preview) {
        item.setPreview(composerBinding.ivPhoto);
        ImageView view = composerBinding.ivPhoto;
        Resources resources = getResources();
        int side = resources.getDimensionPixelSize(R.dimen.compose_media_preview_side);
        int margin = resources.getDimensionPixelSize(R.dimen.compose_media_preview_margin);
        int marginBottom = resources.getDimensionPixelSize(
                R.dimen.compose_media_preview_margin_bottom);
        view.setVisibility(View.VISIBLE);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setImageBitmap(preview);
        view.setOnClickListener(v -> composerViewModel.removeMediaFromQueue(item));
        view.setContentDescription(getString(R.string.action_delete));

        disableMediaButtons();
    }

    @Override
    public void appendToEditor(SpannableStringBuilder builder) {
        int cursorStart = textEditor.getSelectionStart();
        int cursorEnd = textEditor.getSelectionEnd();
        textEditor.append(builder);
        textEditor.setSelection(cursorStart, cursorEnd);
    }

    @Override
    public void cancelFinishingUploadDialog() {
        /*
        if (finishingUploadDialog != null && finishingUploadDialog.isShowing()) {
            finishingUploadDialog.cancel();
        }
        */
    }

    @Override
    public void removeMediaPreview(QueuedMedia item) {
        composerBinding.ivPhoto.setVisibility(View.GONE);
        removeUrlFromEditable(textEditor.getEditableText(), item.getUploadUrl());
        enableMediaButtons();
    }

    private void enableMediaButtons() {
        pickButton.setEnabled(true);
        //ThemeUtils.setDrawableTint(this, pickButton.getDrawable(), R.attr.compose_media_button_tint);
    }

    private void disableMediaButtons() {
        pickButton.setEnabled(false);
        //ThemeUtils.setDrawableTint(this, pickButton.getDrawable(), R.attr.compose_media_button_disabled_tint);
    }

    @Override
    public void displayTransientError(@StringRes int stringId) {
        Snackbar.make(composerBinding.rlRootView, stringId, Snackbar.LENGTH_LONG).show();
    }

    //
    private void setCharsCounter(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charCountTextView.setText(String.valueOf(140 - s.length()));
                if (s.length() > 140) {
                    charCountTextView.setTextColor(ContextCompat.getColor(charCountTextView.getContext(), R.color.colorRed500));
                } else {
                    charCountTextView.setTextColor(Color.parseColor("#9E9E9E"));
                }

                if (s.length() > 0 && s.length() <= 140) {
                    //Limit the number of char in one tweet
                    postButton.setEnabled(true);
                } else {
                    postButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface StatusComposerDialogListener {
        void onPosted(String body);
    }
}