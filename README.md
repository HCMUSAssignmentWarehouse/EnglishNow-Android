# EnglishNow-Android

[![Build Status](https://travis-ci.org/HCMUS-AssignmentWarehouse/EnglishNow-Android-MVVM.svg?branch=master)](https://travis-ci.org/HCMUS-AssignmentWarehouse/EnglishNow-Android-MVVM) [![Coverage Status](https://coveralls.io/repos/github/HCMUS-AssignmentWarehouse/EnglishNow-Android-MVVM/badge.svg?branch=master)](https://coveralls.io/github/HCMUS-AssignmentWarehouse/EnglishNow-Android-MVVM?branch=master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/2d20119e49e2458bbb2dfc9dcd662c40)](https://www.codacy.com/app/nhoxbypass/EnglishNow-Android-MVVM?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=HCMUS-AssignmentWarehouse/EnglishNow-Android-MVVM&amp;utm_campaign=Badge_Grade)

English Now is an awesome Android app based on MVVM architecture with Android Data Binding using Dagger 2, RxJava2, new Android Arch Components, OpenTok client sdk, Firebase and NodeJS server.

It allows a users to practice speaking, writing, chatting in English. By the time using this app, English level of users will be improved.

Time spent: **48** hours spent in total

## Requirements
Apply software architecture techniques inside this [list](https://github.com/HCMUS-AssignmentWarehouse/EnglishNow-Android-MVVM/blob/master/requirement.md)

## User Stories

The basic **required** functionality:

* [x] Find a friend to practice speaking English throught video call
* [x] Chatting with other friends in English
* [x] Writing/updating personal statuses, posts in English


The **extended** features are implemented:

* [ ] User can connect and share post/status with other social network.

The **advance** features are implemented:

* [ ] Improve UI/UX


## Create secrets.xml to provide api key

## Quick deploy to Heroku

Heroku is a PaaS (Platform as a Service) that can be used to deploy simple and small applications for free. To easily deploy **EnglishNow NodeJS server** repository to Heroku, sign up for a Heroku account and click this button:

<a href="https://heroku.com/deploy?template=https://github.com/opentok/learning-opentok-node/" target="_blank">
<img src="https://www.herokucdn.com/deploy/button.png" alt="Deploy">
</a>

Heroku will prompt you to add your OpenTok API key and OpenTok API secret, which you can
obtain at the [TokBox Dashboard](https://dashboard.tokbox.com/keys).

## Video Walkthrough

Here's a walkthrough of implemented user stories:

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/Sw4Gj1eF8is/0.jpg)](https://www.youtube.com/watch?v=Sw4Gj1eF8is)

## Notes

This project use localdb combine with Firebase and NodeJS server, so it cannot provide any method to import data by Firebase JSON files.
Tester **MUST** sign up and sign in in-app.

## Open-source libraries used

- [OpenTok](https://tokbox.com/) - Everything you need to build WebRTC
- [Firebase](https://github.com/firebase/) - build better mobile apps and grow your business
- [RxJava](https://github.com/ReactiveX/RxJava) - Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the JVM.
- [RxAndroid](https://github.com/ReactiveX/Rxandroid) - RxJava bindings for Android
- [Dagger 2](https://github.com/google/dagger) - A fast dependency injector for Android and Java.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) - A collection of libraries that help you design robust, testable, and maintainable apps.
- [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc
- [EventBus](https://github.com/greenrobot/EventBus) - A publish/subscribe event bus for Android and Java that simplifies communication between Activities, Fragments, Threads, Services, etc. Less code, better quality.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API which provides utility on top of Android's normal Log class
- [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) - A circular ImageView for Android


## License

    Copyright 2017 IceTeaViet

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
