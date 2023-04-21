package ljd.calendar.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Player {

    private File music;
    private AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;

    private boolean isPause = false;

    private int pauseIndex = 0;


    public Player(File music) {

        this.music = music;

        try {
            //输入
            this.audioInputStream = AudioSystem.getAudioInputStream(this.music);
            //输出  通过输入适配输出类型
            this.sourceDataLine = AudioSystem.getSourceDataLine(this.audioInputStream.getFormat());
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e + "音频文件不支持");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e + "文件无法读取");
        }


    }

    public void playMusic() {

        try {
            System.out.println(isPause+"  "+pauseIndex);
            sourceDataLine.open();
            sourceDataLine.start();
            int i = 0;
            byte[] bytes = new byte[1024];
            int index=0;
            if (isPause) {
                while (i != -1) {
                    index++;
                    i = audioInputStream.read(bytes, 0, bytes.length);
                    if (index>=pauseIndex) {
                        isPause=false;
                        pauseIndex++;
                        sourceDataLine.write(bytes, 0, bytes.length);
                    }
                }

            } else {

                while (i != -1) {
                    pauseIndex++;
                    if (isPause) {
                        isPause=false;
                        break;
                    }
                    i = audioInputStream.read(bytes, 0, bytes.length);
                    sourceDataLine.write(bytes, 0, bytes.length);
                }
            }



        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println(Thread.currentThread().getName() +"结束");
            sourceDataLine.drain();
            sourceDataLine.close();
        }
    }

    public void stopPlayMusic() {
        isPause=false;
        sourceDataLine.drain();
        sourceDataLine.start();
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
}
