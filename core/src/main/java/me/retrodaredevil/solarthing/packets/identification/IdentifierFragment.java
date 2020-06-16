package me.retrodaredevil.solarthing.packets.identification;

import me.retrodaredevil.solarthing.annotations.NotNull;
import me.retrodaredevil.solarthing.annotations.Nullable;

import java.util.Objects;

/**
 * This is usually used as a unique key that groups a fragmentId and an {@link Identifier} together.
 */
public interface IdentifierFragment extends IdentifierFragmentMatcher { // TODO make all implementations serializable into JSON
	@Nullable Integer getFragmentId();
	@NotNull Identifier getIdentifier();

	@Override
	default boolean matches(IdentifierFragment identifierFragment) {
		return Objects.equals(getFragmentId(), identifierFragment.getFragmentId()) && getIdentifier().equals(identifierFragment.getIdentifier());
	}

	static <T extends Identifier> KnownIdentifierFragment<T> create(Integer fragmentId, T identifier) {
		return new IdentifierFragmentBase<T>(fragmentId, identifier);
	}
}
