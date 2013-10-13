LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := example_jni
LOCAL_SRC_FILES := example.c

# some additional stufs
LOCAL_CFLAGS    := -Werror
LOCAL_LDLIBS    := -llog -landroid	# enable native code to logging

include $(BUILD_SHARED_LIBRARY)
