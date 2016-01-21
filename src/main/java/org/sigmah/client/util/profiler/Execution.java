package org.sigmah.client.util.profiler;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import java.util.Date;
import org.sigmah.client.Sigmah;
import org.sigmah.client.util.JsIterable;
import org.sigmah.offline.appcache.ApplicationCache;
import org.sigmah.offline.js.Values;

/**
 * Execution of a scenario.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public final class Execution extends JavaScriptObject {
	
	/**
	 * Creates a new execution for the given scenario.
	 * 
	 * @param scenario Scenario.
	 * @return A new <code>Execution</code>.
	 */
	public static Execution create(Scenario scenario) {
		final Execution execution = Values.createJavaScriptObject(Execution.class);
		execution.setScenario(scenario);
		execution.setDate(new Date());
		execution.setDuration(0);
		execution.setVersionNumber(Sigmah.VERSION);
		execution.setUserAgent(Window.Navigator.getUserAgent());
		execution.setApplicationCacheStatus(ApplicationCache.getApplicationCache().getStatus().name());
		execution.setCheckpoints(Values.createTypedJavaScriptArray(Checkpoint.class));
		return execution;
	}

	protected Execution() {
		// Not accessible.
	}

	public Scenario getScenario() {
		return Scenario.valueOf(getScenarioName());
	}

	public void setScenario(Scenario scenario) {
		setScenarioName(scenario.name());
	}

	public Date getDate() {
		return Values.getDate(this, "date");
	}

	public void setDate(Date date) {
		Values.setDate(this, "date", date);
	}

	public native String getScenarioName() /*-{
		return this.scenario;
	}-*/;

	public native void setScenarioName(String name) /*-{
		this.scenario = name;
	}-*/;

	public native double getDuration() /*-{
		return this.duration;
	}-*/;
	
	public native void setDuration(double duration) /*-{
		this.duration = duration;
	}-*/;
	
	public native String getUserEmailAddress() /*-{
		return this.userEmailAddress;
	}-*/;
	
	public native void setUserEmailAddress(String userEmailAddress) /*-{
		this.userEmailAddress = userEmailAddress;
	}-*/;

	public native String getVersionNumber() /*-{
		return this.versionNumber;
	}-*/;
	
	public native void setVersionNumber(String versionNumber) /*-{
		this.versionNumber = versionNumber;
	}-*/;

	public native String getUserAgent() /*-{
		return this.userAgent;
	}-*/;
	
	public native void setUserAgent(String userAgent) /*-{
		this.userAgent = userAgent;
	}-*/;
	
	public native boolean isOnline() /*-{
		return this.online;
	}-*/;
	
	public native boolean setOnline(boolean online) /*-{
		this.online = online;
	}-*/;
	
	public native String getApplicationCacheStatus() /*-{
		return this.applicationCacheStatus;
	}-*/;
	
	public native void setApplicationCacheStatus(String applicationCacheStatus) /*-{
		this.applicationCacheStatus = applicationCacheStatus;
	}-*/;
	
	public native JsArray<Checkpoint> getCheckpoints() /*-{
		return this.checkpoints;
	}-*/;
	
	public Iterable<Checkpoint> getCheckpointSequence() {
		return new JsIterable<Checkpoint>(getCheckpoints());
	}
	
	public native void setCheckpoints(JsArray<Checkpoint> checkpoints) /*-{
		this.checkpoints = checkpoints;
	}-*/;
	
	/**
	 * Adds a new checkpoint.
	 * 
	 * @param name Description of the checkpoint.
	 * @param time Time when the checkpoint has been reached.
	 */
	public native void addCheckpoint(String name, double time) /*-{
		this.checkpoints.push({"name": name, "time": time, "duration": 0});
	}-*/;
	
}
