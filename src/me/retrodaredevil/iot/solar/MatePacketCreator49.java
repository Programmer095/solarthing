package me.retrodaredevil.iot.solar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import me.retrodaredevil.iot.packets.PacketCreator;
import me.retrodaredevil.iot.packets.Packet;
import me.retrodaredevil.iot.solar.fx.FXStatusPackets;
import me.retrodaredevil.iot.solar.mx.MXStatusPackets;
import me.retrodaredevil.iot.util.CheckSumException;
import me.retrodaredevil.iot.util.IgnoreCheckSum;
import me.retrodaredevil.iot.util.ParsePacketAsciiDecimalDigitException;
import me.retrodaredevil.util.json.JsonFile;

public class MatePacketCreator49 implements PacketCreator {
	private static final char START = 10; // \n
	private static final char END = 13; // \r
	private static final char NULL_CHAR = 0;

	private final IgnoreCheckSum ignoreCheckSum;
	private final char[] bytes = new char[49];
	/** The amount of char elements initialized in the bytes array */
	private int amount = 0;

	public MatePacketCreator49(IgnoreCheckSum ignoreCheckSum){
		this.ignoreCheckSum = ignoreCheckSum;
	}
	public MatePacketCreator49(){
		this(IgnoreCheckSum.DISABLED);
	}

	//Public Methods
	@Override
	public Collection<Packet> add(char[] chars){ // TODO set this up to tolerate (and ignore) packets that aren't 49 bytes (utilizing END char)
		if(chars.length == 0){
			return Collections.emptySet();
		}
		char first = chars[0];
		if(amount == 0 && first != START){
			return Collections.emptySet(); // gotta wait for the start char
		}
		List<Packet> r = null;
		for(char c : chars){
			bytes[amount] = c;
			amount++;
			int length = bytes.length;
			if(amount > length) {
				System.err.println("amount should never be > length because we should have reset it.");
			}
			if(amount == length){
				try{
					final Packet packet = create(); // resets bytes array and amount

					System.out.println();
					System.out.println("============");
					System.out.println(JsonFile.gson.toJson(packet));
					System.out.println("============");
					System.out.println();

					if(r == null){
						r = new ArrayList<>();
					}
					r.add(packet);
				} catch(CheckSumException | UnsupportedOperationException | ParsePacketAsciiDecimalDigitException ex){
					ex.printStackTrace();
					System.err.println("Got an exception that we were able to handle. Ignoring it...");
				} finally {
					reset();
				}

			}
		}
		if(r == null){
			return Collections.emptySet();
		}
		return r;
	}
	private Packet create() throws CheckSumException, ParsePacketAsciiDecimalDigitException, UnsupportedOperationException {
		final int value = (int) bytes[1]; // ascii value
		if(value >= 48 && value <= 58){ // fx status
			return FXStatusPackets.createFromChars(bytes, ignoreCheckSum);
		} else if(value >= 65 && value <= 75){ // mx
			return MXStatusPackets.createFromChars(bytes, ignoreCheckSum);
		} else if(value >= 97 && value <= 106){
			throw new UnsupportedOperationException("Not set up to use FLEXnet DC Status Packets. value: " + value);
		} else {
			throw new UnsupportedOperationException("Ascii value: " + value + " not supported. (from: '" + new String(bytes) + "')");
		}
	}
	private void reset(){
		amount = 0;
		for(int i = 0; i < bytes.length; i++){
			bytes[i] = 0; // reset bytes array
		}
	}

}