#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <android/log.h>

//#ifndef ANDROID_LOG_VERBOSE
//#define ANDROID_LOG_VERBOSE
//#endif

// ******************** Const and macros ***************************

#define APPNAME "MainActivity"	// Must point to real application name

#define LOGV(text, parameters)	__android_log_print(ANDROID_LOG_VERBOSE, APPNAME, text, parameters)


// ******************** definitions ********************************
jint* createPrimitiveIntArray(jint size);


// ******************** Implemenataion *****************************


// JNIEXPORT void JNICALL		// albo
void							// same "void"
Java_com_example_jni1_MainActivity_nativeFromJava( JNIEnv* env,
                                                  jobject thiz )
{
    //return (*env)->NewStringUTF(env, "Hello from JNI !");
}

//JNIEXPORT jstring JNICALL		// albo ...
jstring // same jstring wystarcza
Java_com_example_jni1_MainActivity_nativeStringFromJava( JNIEnv* env,
                                                  jobject thiz )
{
	// logging feature
	__android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "The value of 1 + 1 is %d", 1+1);
	__android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "The value NONE","");
	LOGV("Use compact MACRO","");

	char arrText[255] = {};
	strcpy(arrText, "Some native text 8 ");
	strcat(arrText, "Hello from JNI !");
//    return (*env)->NewStringUTF(env, "Hello from JNI !");
    return (*env)->NewStringUTF(env, arrText);
}


//JNIEXPORT int JNICALL
//jint
jsize		// because:  "typedef jint jsize;"
Java_com_example_jni1_MainActivity_nativePassingArray( JNIEnv* env, jobject thiz,
														jintArray intArray)
{
	jint *iArr   = (*env)->GetIntArrayElements(env, intArray, 0);
	jsize length = (*env)->GetArrayLength     (env, intArray);
    return length;
}



jintArray
Java_com_example_jni1_MainActivity_nativeRetArray( JNIEnv* env, jobject thiz,
														jint size)
{
	LOGV("nativeRetArray() invoked in native", "");
#define START 0

	jint* primitiveArray = createPrimitiveIntArray(size);				// Get primitive int array

	jintArray jintArrayObject   = (*env)->NewIntArray(env, size);		// Create int array object

	(*env)->SetIntArrayRegion(env, jintArrayObject, START, size, primitiveArray); // fulfill array object with data from primitive array

    return jintArrayObject;
}

/**
 * Creates primitive int array and fulfill some not random data
 */
jint* createPrimitiveIntArray(jint size){
	jint* tmpPrimitiveArray = (jint*)malloc(size * sizeof(jint));
	jint i;

	for(i = 0; i < size; i++){
		tmpPrimitiveArray[i] = (jint)(size - i);
	}

	return tmpPrimitiveArray;
}

