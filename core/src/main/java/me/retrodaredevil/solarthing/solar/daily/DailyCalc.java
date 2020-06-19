package me.retrodaredevil.solarthing.solar.daily;

import me.retrodaredevil.solarthing.packets.TimestampedPacket;
import me.retrodaredevil.solarthing.solar.common.DailyData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DailyCalc {
	private DailyCalc() { throw new UnsupportedOperationException(); }

	public static <T extends DailyData> float getSumTotal(Collection<? extends List<DailyPair<T>>> dailyPairListCollection, TotalGetter<T> totalGetter) {
		float total = 0;
		for (List<DailyPair<T>> dailyPairs : dailyPairListCollection) {
			total += getTotal(dailyPairs, totalGetter);
		}
		return total;
	}
	public static <T extends DailyData> float getTotal(List<? extends DailyPair<T>> dailyPairs, TotalGetter<T> totalGetter) {
		float total = 0;
		for (DailyPair<T> dailyPair : dailyPairs) {
			if (dailyPair.getStartPacketType() == DailyPair.StartPacketType.CUT_OFF) {
				total += totalGetter.getTotal(dailyPair.getLatestPacket().getPacket()) - totalGetter.getTotal(dailyPair.getStartPacket().getPacket());
			} else {
				total += totalGetter.getTotal(dailyPair.getLatestPacket().getPacket());
			}
		}
		return total;
	}
	public static <T extends DailyData> List<SumNode> getTotals(List<DailyPair<T>> dailyPairs, TotalGetter<T> totalGetter, List<TimestampedPacket<T>> packets) {
		List<SumNode> r = new ArrayList<>();
		for (TimestampedPacket<T> packet : packets) {
			long dateMillis = packet.getDateMillis();
			List<DailyPair<T>> previousDailyPairs = new ArrayList<>();
			for (DailyPair<T> element : dailyPairs) {
				if (dateMillis > element.getLatestPacket().getDateMillis()) {
					break;
				}
				previousDailyPairs.add(element);
			}
			if (previousDailyPairs.isEmpty()) {
				throw new IllegalArgumentException("Could not find DailyPair for dateMillis=" + dateMillis);
			}
			DailyPair<T> lastDailyPair = previousDailyPairs.get(previousDailyPairs.size() - 1);
			previousDailyPairs.set(previousDailyPairs.size() - 1, new DailyPair<>(lastDailyPair.getStartPacket(), packet, lastDailyPair.getStartPacketType()));
			float sum = getTotal(previousDailyPairs, totalGetter);
			r.add(new SumNode(sum, dateMillis));
		}
		return r;
	}
	@FunctionalInterface
	public interface TotalGetter<T> {
		float getTotal(T t);
	}
	public static final class SumNode {
		private final float sum;
		private final long dateMillis;

		public SumNode(float sum, long dateMillis) {
			this.sum = sum;
			this.dateMillis = dateMillis;
		}

		public float getSum() {
			return sum;
		}

		public long getDateMillis() {
			return dateMillis;
		}
	}
}
