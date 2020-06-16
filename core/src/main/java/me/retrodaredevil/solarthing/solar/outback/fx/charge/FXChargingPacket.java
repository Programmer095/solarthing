package me.retrodaredevil.solarthing.solar.outback.fx.charge;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.retrodaredevil.solarthing.annotations.JsonExplicit;
import me.retrodaredevil.solarthing.solar.extra.SolarExtraPacketType;
import me.retrodaredevil.solarthing.solar.extra.SupplementarySolarExtraPacket;
import me.retrodaredevil.solarthing.solar.outback.OutbackData;
import me.retrodaredevil.solarthing.annotations.Nullable;

import me.retrodaredevil.solarthing.annotations.NotNull;

/**
 * Note that data from this was never accurate because it didn't take temperature compensation into account
 */
@JsonDeserialize(as = ImmutableFXChargingPacket.class)
@JsonTypeName("FX_CHARGING")
@JsonExplicit
@Deprecated
public interface FXChargingPacket extends SupplementarySolarExtraPacket, OutbackData {
	@NotNull
	@Override
	default SolarExtraPacketType getPacketType(){
		return SolarExtraPacketType.FX_CHARGING;
	}
	@JsonProperty("masterFXAddress") // TODO we WILL change this to just "address" in the future
	@JsonAlias("address") // we'll flip these two in the future
	@Override
	int getAddress();

	@JsonProperty("fxChargingMode")
	@Nullable FXChargingMode getFXChargingMode();

	@JsonProperty("remainingAbsorbTimeMillis")
	long getRemainingAbsorbTimeMillis();
	@JsonProperty("remainingFloatTimeMillis")
	long getRemainingFloatTimeMillis();
	@JsonProperty("remainingEqualizeTimeMillis")
	long getRemainingEqualizeTimeMillis();


	@JsonProperty("totalAbsorbTimeMillis")
	long getTotalAbsorbTimeMillis();
	@JsonProperty("totalFloatTimeMillis")
	long getTotalFloatTimeMillis();
	@JsonProperty("totalEqualizeTimeMillis")
	long getTotalEqualizeTimeMillis();

}
