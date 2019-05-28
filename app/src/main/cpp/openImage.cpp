//
// Created by nate on 5/21/19.
//

#include <jni.h>
#include <fstream>

#include "int_types.h"

enum file_types {BMP, JPG, GIF, PNG};
int readJpeg(const char* fileName, char* data);


extern "C"
JNIEXPORT jstring JNICALL Java_com_ergv_portfolio_imagesmanipulator_ImageEditActivity_openFile(JNIEnv* env, jobject obj, jstring jstr){
    const char* chs  = env->GetStringUTFChars(jstr, 0);
    std::string str(chs);
    env->ReleaseStringUTFChars(jstr, chs);
    int len = str.length();
    for(int k=0; k<len/2; k++){
        int m = len-k-1;
        str[k]^=str[m];
        str[m]^=str[k];
        str[k]^=str[m];
    }

    return env->NewStringUTF(str.c_str());
}


extern "C" JNIEXPORT jcharArray JNICALL
Java_com_ergv_portfolio_imagesmanipulator_ImageEditActivity_getBytes(JNIEnv* env, jobject obj, jstring filePath, jint fileYtpe){
    const char* c_filePath  = env->GetStringUTFChars(filePath, 0);
    char* buffer;
    int bytesRead;
    switch(fileYtpe){

        case BMP:
        case JPG:

        case PNG:
        case GIF:
            bytesRead = readJpeg(c_filePath, buffer);
            break;
    }


    jcharArray data = env->NewCharArray(bytesRead);
    env->SetCharArrayRegion(data, 0, bytesRead, (const jchar*)buffer);
    return data;
}


int readJpeg(const char* c_filePath, char* data){

    std::ifstream is (c_filePath, std::ifstream::binary);
    char DQT[69];
    is.read (DQT,2*sizeof(u8));
    is.read (DQT,69*sizeof(u8));
    is.close();
    return 4;

}
