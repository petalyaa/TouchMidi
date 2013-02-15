package org.pet.touchmidi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.pet.touchmidi.util.SystemUiHider;

import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class TouchMidiActivity extends Activity {
	
	private static final File OUTPUT_DIRECTORY = Environment.getExternalStorageDirectory();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_touch_midi);
		
		GestureOverlayView midiTouchPadInput = (GestureOverlayView) findViewById(R.id.midiTouchPad);
		midiTouchPadInput.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
			
			private AudioTrack track;
			
			private boolean stop = false;
			
			private short[] buffer = new short[1024];
			
//			private FileInputStream fis;
//			
//			private File output;
//			
//			private boolean alreadyStarted = false;
			
			@Override
			public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
//				File midiDirectory = new File(OUTPUT_DIRECTORY, "midi");
//				if(!midiDirectory.exists()) {
//					midiDirectory.mkdir();
//				}
//				output = new File(midiDirectory, "exampleout.mid");
//				try {
//					if(!output.exists())
//						output.createNewFile();
//					fis = new FileInputStream(output);
//					alreadyStarted = true;
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				
				int mode = AudioTrack.MODE_STREAM;
				int sampleRate = AudioTrack.getNativeOutputSampleRate(mode);
				int pcmEncoding = AudioFormat.ENCODING_PCM_16BIT;
				int channelOut = AudioFormat.CHANNEL_OUT_MONO;
				int streamType = AudioManager.STREAM_MUSIC;
				
				int minSize = AudioTrack.getMinBufferSize(sampleRate, channelOut, pcmEncoding);
				track = new AudioTrack(streamType, sampleRate, channelOut, pcmEncoding, minSize, mode);
				track.play();    
				stop = false;
			}
			
			@Override
			public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
//				try {
//					if(fis != null)
//						fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				output.delete();
//				alreadyStarted = false;
				stop = true;
				track.stop();
				track.release();
				track = null;
			}
			
			@Override
			public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
				onGestureEnded(overlay, event);
			}
			
			private void fillBuffer(float[] samples) {
				if (buffer.length < samples.length) {
					buffer = new short[samples.length];
				}

				for (int i = 0; i < samples.length; i++) {
					buffer[i] = (short) (samples[i] * Short.MAX_VALUE);
				}
			}
			
			@Override
			public void onGesture(GestureOverlayView overlay, MotionEvent event) {
				int x = (int) event.getX();
				int y = (int) event.getY();
//				
				final float frequency = x + y;
				
//				if(alreadyStarted) {
//					MidiPlayerThread t = new MidiPlayerThread(x, y, output, fis);
//					t.run();
//				}
//				final float frequency = x;
	            float increment = (float)(2*Math.PI) * frequency / 44100; // angular increment for each sample
	            float angle = 0;
	            float samples[] = new float[2014];
	            
	            for (int i = 0; i < samples.length; i++) {
	            	samples[i] = (float) Math.sin(angle);
	            	angle += increment;
	            }
	            
//				float[] samples = {60, 62};
	            fillBuffer(samples);
	            track.write(buffer, 0, samples.length);
			}
		});
	}


}
