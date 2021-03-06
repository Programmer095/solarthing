package me.retrodaredevil.solarthing.solar.renogy.rover.modbus;

import me.retrodaredevil.io.modbus.ModbusMessage;
import me.retrodaredevil.io.modbus.ModbusMessages;
import me.retrodaredevil.io.modbus.handling.HandleResponseHelper;
import me.retrodaredevil.io.modbus.handling.MessageHandler;
import me.retrodaredevil.io.modbus.handling.ParsedResponseException;

public class RoverMessageHandler implements MessageHandler<Void> {
	private final int functionCode;

	protected RoverMessageHandler(int functionCode) {
		this.functionCode = functionCode;
	}

	public static MessageHandler<Void> createRoverFactoryReset(){
		return new RoverMessageHandler(0x78);
	}
	public static MessageHandler<Void> createRoverClearHistory(){
		return new RoverMessageHandler(0x79);
	}

	@Override
	public ModbusMessage createRequest() {
		return ModbusMessages.createMessage((byte) functionCode, new byte[] {0, 0, 0, 1});
	}

	@Override
	public Void handleResponse(ModbusMessage response) {
		HandleResponseHelper.checkResponse(response, this.functionCode, 4);

		int[] data = response.getData();

		if(data[0] != 0) throw new ParsedResponseException(response, "data[0] must be 0! It's: " + data[0]);
		if(data[1] != 0) throw new ParsedResponseException(response, "data[1] must be 0! It's: " + data[1]);
		if(data[2] != 0) throw new ParsedResponseException(response, "data[2] must be 0! It's: " + data[2]);
		if(data[3] != 1) throw new ParsedResponseException(response, "data[3] must be 1! It's: " + data[3]);
		return null;
	}
}
