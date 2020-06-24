package me.retrodaredevil.solarthing.packets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import me.retrodaredevil.solarthing.annotations.NotNull;

@JsonPropertyOrder({"packetType"}) // we want packetType to always be at the top
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "packetType")
public interface DocumentedPacket extends Packet {
	/**
	 * Should be serialized as "packetType"
	 * @return The packet type
	 */
	@JsonProperty(value = "packetType")
	@NotNull DocumentedPacketType getPacketType();
}
