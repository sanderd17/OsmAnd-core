LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

PROJECT_ROOT_RELATIVE := ../../../../platforms/android/OsmAnd
OSMAND_LIBPNG_ROOT_RELATIVE := ../../../externals/libpng
OSMAND_LIBPNG_ROOT := $(LOCAL_PATH)/$(OSMAND_LIBPNG_ROOT_RELATIVE)
OSMAND_LIBPNG_RELATIVE := ../../../externals/libpng/upstream.patched
OSMAND_LIBPNG := $(LOCAL_PATH)/$(OSMAND_LIBPNG_RELATIVE)

LOCAL_C_INCLUDES += \
	$(OSMAND_LIBPNG)
	
LOCAL_CFLAGS += -DPNG_CONFIGURE_LIBPNG -fPIC

LOCAL_SRC_FILES := \
	$(OSMAND_LIBPNG_RELATIVE)/png.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngerror.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngget.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngmem.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngpread.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngread.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngrio.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngrtran.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngrutil.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngset.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngtrans.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngwio.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngwrite.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngwtran.c \
	$(OSMAND_LIBPNG_RELATIVE)/pngwutil.c

ifneq ($(OSMAND_BUILDING_NEON_LIBRARY),true)
	LOCAL_MODULE := osmand_png
else
	LOCAL_MODULE := osmand_png_neon
	LOCAL_ARM_NEON := true
endif

ifneq ($(OSMAND_USE_PREBUILT),true)
	include $(BUILD_STATIC_LIBRARY)
else
	LOCAL_SRC_FILES := \
		$(PROJECT_ROOT_RELATIVE)/libs/$(TARGET_ARCH_ABI)/lib$(LOCAL_MODULE).a
	include $(PREBUILT_STATIC_LIBRARY)
endif