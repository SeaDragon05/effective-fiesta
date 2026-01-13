#ifndef GLOBALVARS_H
#define GLOBALVARS_H
#include <stdbool.h>
#include <stdio.h>
#include <string>

int    LM_compile_chunks_amount = 4;
bool   LM_compile_multi_thread = false;
int    LM_error_count = 0;
bool   LM_generate_chaos = true;
bool   LM_generation_filter = true;
int    LM_generate_sentence_word_limit = 50;
bool   LM_save_data = false;
bool   LM_test_use_wikipedia = false;
bool   LM_debug = false;

//Database data;

bool   general_settings_console_mode = false;
bool   general_settings_enable_extra_data = false;
bool   general_settings_enable_music = true;
bool   general_settings_enable_TTS = true;

//Window window;
int    window_line_count = 0;

//please don't read the following:
std::string bannedWords[] = {"fuck", "fucking", "fucked", "fuckin", "fuckin'", "shit", "ass", "porn", "cocaine", "fucc", "sex", "sexuality", "sexually", "tits", "dick", "penis", "vagina", "gay", "bitch", "rape", "raping", "twerk", "penetration", "shitposts", "hentai", "cock", "masturbate", "masturbation", "hell", "meth", "cum", "erect", "titties", "fleshlight", "cumming", "fucks", "shitpost", "naked", "nude", "shitty", "dumbass", "motherfucker", "bullshit", "masturbated", "nazi", "nazis", "dammit", "std", "sexual", "cock-sucking", "homosexual", "heterosexual", "damn", "damnit", "SLUTTIEST", "CUMSUCKER", "fucker's", "cunt", "motherfuckin", "semen", "ejaculated", "Putin", "gender/sex", "asshole", "boobs", "horny", "foreskin", "faggots"};
//admit it, you read it
#endif

