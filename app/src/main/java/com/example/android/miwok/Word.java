package com.example.android.miwok;


public class Word {

 private String mEnglishTranslation;
    private String mMewaxTranslation;
    private int mImageid=NON_image_state;
    private static int NON_image_state=-1;
    private int soundID;
    public Word (String englishTranslation,String mewaxtranslation,int sound ){
        mEnglishTranslation = englishTranslation;
        mMewaxTranslation = mewaxtranslation;
        soundID=sound;
    }
    public Word(String englishTranslation,String mewaxtranslation,int mimageid ,int sound){
        mEnglishTranslation = englishTranslation;
        mMewaxTranslation = mewaxtranslation;
        mImageid = mimageid;
        soundID=sound;
    }
    public String getEnglishTranslation(){
        return mEnglishTranslation;
    }
    public String getmMewaxTranslation(){
        return mMewaxTranslation;
    }
    public int getmImageid(){
        return mImageid;
    }
    public boolean hasimage(){
        return mImageid != NON_image_state;
    }
    public int getAudioId(){return soundID;}




}

