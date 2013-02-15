//package org.pet.touchmidi;
//
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileDescriptor;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import android.media.AudioFormat;
//import android.media.AudioManager;
//import android.media.AudioTrack;
//import android.media.MediaPlayer;
//import android.util.Log;
//
//public class MidiPlayerThread extends Thread {
//	
//	private int x;
//	
//	private int y;
//	
//	private final byte NoteOn = (byte) 144;
//	
//    private final byte NoteOff = (byte) 128;
//    
//    private final byte defaultVolume = (byte) 100;
//    
//    private final int  defaultTPQ = 480;
//    
//    private FileInputStream fis = null;
//    
//    private File output;
//	
//	private ByteArrayOutputStream track = null;
//	
//	final int c0 = 48, d0 = 50, e0 = 52, f0 = 53, g0 = 55, a0 = 57, b0 = 59,
//			c = 60, d = 62, e = 64, f = 65, g = 67, a = 69, b = 71, c1 = 72,
//			d1 = 74, e1 = 76, f1 = 77, g1 = 79, a1 = 81, b1 = 83, c2 = 84,
//			rest = 0;
//	
//	private final int[] notes = {48, 50, 52, 53, 55, 57, 59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 0};
//	
//	public MidiPlayerThread(int x, int y, File output, FileInputStream fis) {
//		this.x = x;
//		this.y = y;
//		this.output = output;
//		this.fis = fis;
//		this.track = new ByteArrayOutputStream();
//	}
//	
//	public synchronized void run() {
//
//		play(notes[0], 1);
//		
//		DataOutputStream data = null;
//        try {
//			data = new DataOutputStream(new FileOutputStream(output));
//			data.writeBytes("MThd");
//	        data.writeInt(6);
//	        data.writeInt(1);
//	        data.writeShort((short) defaultTPQ);
//	        data.writeBytes("MTrk");
//	        data.writeInt(track.size() + 4);
//	        data.write(track.toByteArray() );
//	        data.writeInt(16723712);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(data != null)
//					data.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			try {
//				if(track != null)
//					track.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
////        MediaPlayer mediaPlayer = new MediaPlayer();
////			fis = new FileInputStream(output);
////			FileDescriptor fd = fis.getFD();
////	        mediaPlayer.setDataSource(fd);
////	        mediaPlayer.prepare();
////	        mediaPlayer.start();
////	        int duration = mediaPlayer.getDuration();
////	        try {
////				Thread.sleep(duration);
////			} catch (InterruptedException e) {
////				e.printStackTrace();
////			}
////	        mediaPlayer.stop();
//	        
//	        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 4100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, 1024, AudioTrack.MODE_STREAM);
////	        audioTrack.
//        
//       
//	}
//
//	private void play(int pitch, double duration) {
//		int tpq = 480;
//		int timeSinceLastNote = 0;
//		//int durat = (int)(duration * tpq / 4);
//		int durat = 50;
//		Log.d("play", "Duration : " + durat);
//
//        if (pitch==0) {
//            timeSinceLastNote += durat;
//        } else {
//            sendLength(timeSinceLastNote);
//            sendByte(NoteOn);
//            sendByte((byte) pitch);
//            sendByte(defaultVolume);
//
//            sendLength(durat);
//            sendByte(NoteOff);
//            sendByte((byte) pitch);
//            sendByte(defaultVolume);
//            timeSinceLastNote = 0;
//        }
//    }
//	
//	private void sendLength(int x) {
//        if (x >= 2097152) {
//            sendByte((byte) (128 + x / 2097152));
//            x %= 2097152;
//        }
//        if (x >= 16384) {
//            sendByte((byte) (128 + x / 16384));
//            x %= 16384;
//        }
//        if (x >= 128) {
//            sendByte((byte) (128 + x / 128));
//            x %= 128;
//        }
//        sendByte((byte)x);
//    }
//	
//	private void sendByte(byte b) {
//        track.write(b);
//    }
//
//	
//}
