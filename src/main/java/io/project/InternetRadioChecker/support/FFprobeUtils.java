package io.project.InternetRadioChecker.support;

import io.project.InternetRadioChecker.exceptions.TimeoutException;
import io.project.InternetRadioChecker.support.wait.Wait;
import javazoom.jl.decoder.Header;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;

public class FFprobeUtils {

    private static final Logger log = LoggerFactory.getLogger(FFprobeUtils.class);

    private static FFprobe ffprobe;
    static {
        try {
            ffprobe = new FFprobe("ffmpeg/bin/ffprobe.exe");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public record ProbeResult(String fileName, String codec, String bitrate) {};

    public static ProbeResult getProbeWithTimeout(String mediaPath) throws TimeoutException {
        Wait<Header> wait = new Wait<>();
        return wait.until(x -> getProbe(mediaPath));
    }

    public static @Nullable ProbeResult getProbe(String mediaPath) {
        try {
            return getProbe(mediaPath, "BASS/2.4");
        } catch (IOException e) {
            return null;
        }
    }

    public static ProbeResult getProbe(String mediaPath, String userAgent) throws IOException {
        FFmpegProbeResult probeResult = ffprobe.probe(mediaPath, userAgent);
        FFmpegFormat format = probeResult.getFormat();
        FFmpegStream stream = probeResult.getStreams().get(0);
        String bitrate = format.bit_rate > 0 ? String.format("%dkbps",format.bit_rate / 1000) : "0";
        log.info("File: {}, Codec: {}, Bitrate: {}", format.filename, stream.codec_name, bitrate);
        return new ProbeResult(format.filename, stream.codec_name, bitrate);
    }




}
