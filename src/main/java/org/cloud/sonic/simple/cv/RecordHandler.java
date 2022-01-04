package org.cloud.sonic.simple.cv;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * @author ZhouYiXun
 * @des byte数组转换mp4
 * @date 2022/1/4 21:49
 */
@Component
public class RecordHandler {
    public static String record(List<byte[]> images, int width, int height, int chunk, int total) throws FrameRecorder.Exception {
        synchronized (RecordHandler.class) {
            long time = Calendar.getInstance().getTimeInMillis();
            File file = new File("temp" + File.separator + time + ".mp4");
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(file.getAbsoluteFile(), width, height);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFrameRate(24);
            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            recorder.setFormat("mp4");
            try {
                recorder.start();
                Java2DFrameConverter converter = new Java2DFrameConverter();
                // 录制视频设置24帧
                int duration = images.size() / 24;
                for (int i = 0; i < duration; i++) {
                    for (int j = 0; j < 24; j++) {
                        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(images.get(i * 24 + j)));
                        recorder.record(converter.getFrame(bufferedImage));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                recorder.stop();
                recorder.release();
            }
            if (chunk == total - 1) {

            }
//            return UploadTools.uploadPatchRecord(file);
        }
    }
}
