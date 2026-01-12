package com.sun.speech.freetts.en.us.cmu_us_kal;

import com.sun.speech.freetts.en.us.CMUDiphoneVoice;
import java.util.Locale;
import com.sun.speech.freetts.Age;
import com.sun.speech.freetts.Gender;
import com.sun.speech.freetts.en.us.CMULexicon;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceDirectory;

public class KevinVoiceDirectory extends VoiceDirectory
{
    public Voice[] getVoices() {
        final CMULexicon lexicon = new CMULexicon("cmulex");
        final Voice kevin = (Voice)new CMUDiphoneVoice("kevin", Gender.MALE, Age.YOUNGER_ADULT, "default 8-bit diphone voice", Locale.US, "general", "cmu", lexicon, this.getClass().getResource("cmu_us_kal.bin"));
        final Voice kevin2 = (Voice)new CMUDiphoneVoice("kevin16", Gender.MALE, Age.YOUNGER_ADULT, "default 16-bit diphone voice", Locale.US, "general", "cmu", lexicon, this.getClass().getResource("cmu_us_kal16.bin"));
        final Voice[] voices = { kevin, kevin2 };
        return voices;
    }
    
    public static void main(final String[] args) {
        System.out.println(new KevinVoiceDirectory().toString());
    }
}