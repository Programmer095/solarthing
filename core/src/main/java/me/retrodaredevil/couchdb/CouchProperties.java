package me.retrodaredevil.couchdb;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.retrodaredevil.solarthing.annotations.NotNull;
import me.retrodaredevil.solarthing.annotations.Nullable;

@JsonDeserialize(as = ImmutableCouchProperties.class)
public interface CouchProperties {
	@NotNull
	String getProtocol();
	@NotNull
	String getHost();
	@Nullable
	String getPath();
	int getPort();

	@Nullable
	String getUsername();
	@Nullable
	String getPassword();

	/**
	 * @return The socket timeout in ms
	 */
	@Nullable
	Integer getSocketTimeoutMillis();
	/**
	 * @return The connection timeout in ms
	 */
	@Nullable
	Integer getConnectionTimeoutMillis();
	@Nullable
	Integer getMaxConnections();
	@Nullable
	String getProxyHost();
	@Nullable
	Integer getProxyPort();
}
