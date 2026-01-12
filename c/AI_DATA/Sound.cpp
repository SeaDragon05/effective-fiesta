#include<stdio.h>
#include<SDL2/SDL.h>

#ifdef USESDL
#include <SDL.h>
#include <SDL_audio.h>
#endif

extern bool general_settings_enable_music;

Uint32 wavLength;
Uint8 *wavBuffer;
SDL_AudioDeviceID deviceId;

int println(const char *str1) {
  printf("%s\n", str1);
  printf("\n");
  return 0;
}

// Function to play a WAV file given its path
void playWav(const char *filePath) {
    if (!general_settings_enable_music) return;
    SDL_AudioSpec wavSpec;
    Uint32 wavLength;
    Uint8 *wavBuffer;

    if (SDL_Init(SDL_INIT_AUDIO) < 0) {
        fprintf(stderr, "Failed to initialize SDL: %s\n", SDL_GetError());
        return;
    }

    if (SDL_LoadWAV(filePath, &wavSpec, &wavBuffer, &wavLength) == NULL) {
        fprintf(stderr, "Failed to load WAV file: %s\n", SDL_GetError());
        SDL_Quit();
        return;
    }

    SDL_AudioDeviceID deviceId = SDL_OpenAudioDevice(NULL, 0, &wavSpec, NULL, 0);
    if (deviceId == 0) {
        fprintf(stderr, "Failed to open audio device: %s\n", SDL_GetError());
        SDL_FreeWAV(wavBuffer);
        SDL_Quit();
        return;
    }

    SDL_PauseAudioDevice(deviceId, 0);
    SDL_QueueAudio(deviceId, wavBuffer, wavLength);
    SDL_Delay(wavLength/100); // Wait for the sound to finish playing

    SDL_CloseAudioDevice(deviceId);
    SDL_FreeWAV(wavBuffer);
    SDL_Quit();
}

void playWavNoHalt(const char *filePath) {
    if (!general_settings_enable_music) return;

    // Initialize SDL Audio if it's not already initialized
    if (SDL_Init(SDL_INIT_AUDIO) < 0) {
        fprintf(stderr, "Failed to initialize SDL: %s\n", SDL_GetError());
        return;
    }

    // Load the WAV file
    SDL_AudioSpec wavSpec;
    if (SDL_LoadWAV(filePath, &wavSpec, &wavBuffer, &wavLength) == NULL) {
        fprintf(stderr, "Failed to load WAV file: %s\n", SDL_GetError());
        SDL_Quit();
        return;
    }

    // Open the audio device
    deviceId = SDL_OpenAudioDevice(NULL, 0, &wavSpec, NULL, 0);
    if (deviceId == 0) {
        fprintf(stderr, "Failed to open audio device: %s\n", SDL_GetError());
        SDL_FreeWAV(wavBuffer);
        SDL_Quit();
        return;
    }

    // Start audio playback
    SDL_PauseAudioDevice(deviceId, 0);
    SDL_QueueAudio(deviceId, wavBuffer, wavLength);

    // Optionally, you could free the WAV buffer here if you do not need it anymore.
    // SDL_FreeWAV(wavBuffer); // Commented out to allow continued playback.
}

void cleanupAudio() {
    if (deviceId != 0) {
        SDL_CloseAudioDevice(deviceId);
        deviceId = 0;
    }
    if (wavBuffer != nullptr) {
        SDL_FreeWAV(wavBuffer);
        wavBuffer = nullptr;
    }
    SDL_Quit();
}

