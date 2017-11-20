# vowelrecognition

## Description

This is my bachelors degree diploma project. It uses the microphone to listen to you and can recognize the vowels you speak. Say only vowels, not whole words. It has to be trained to adjust to your voice.

## Technical details

It is a plain java program which does not make use of other libraries. In very few details, this is how it works:
1. Listens for raw data from the microphone.
1. Does a fourier transformation on the signal from the microphone.
1. Performs cepstrum analysis on different lengths of time (windows) from the recording.
1. The cepstrum analysis on these windows is used when thaining to compute an average for each vowel. Actually they are primary sounds, so it should differentiate between a fork hitting wood and a fork hitting another metal too.
1. When recognizing, the same algorithm is applied and the result is compared to the average for each vowel. It shows the one which is closer.

## How to build and run.

### Prerequisites

* Apache Ant: http://ant.apache.org/

### How to build

`ant build` : This will build a runnable jar.

### How to run

`ant run` : This will build and run the runnable jar.
