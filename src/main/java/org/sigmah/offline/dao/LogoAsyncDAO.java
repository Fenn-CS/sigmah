package org.sigmah.offline.dao;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.sigmah.offline.indexeddb.ObjectStore;
import org.sigmah.offline.indexeddb.Request;
import org.sigmah.offline.indexeddb.Store;
import org.sigmah.offline.indexeddb.Transaction;

/**
 *
 * @author Raphaël Calabro (rcalabro@ideia.fr)
 */
public class LogoAsyncDAO extends BaseAsyncDAO {

	public void saveOrUpdate(final int organizationId, final String logo) {
		openTransaction(Transaction.Mode.READ_WRITE, new OpenTransactionHandler() {

			@Override
			public void onTransaction(Transaction transaction) {
				final ObjectStore store = transaction.getObjectStore(getRequiredStore());
				
				store.put(boxLogo(organizationId, logo)).addCallback(new AsyncCallback<Request>() {

					@Override
					public void onFailure(Throwable caught) {
						Log.error("Error while saving logo for organization " + organizationId + ".", caught);
					}

					@Override
					public void onSuccess(Request result) {
						// Success.
					}
				});
			}
		});
	}
	
	public void get(final int organizationId, final AsyncCallback<String> callback) {
		openTransaction(Transaction.Mode.READ_ONLY, new OpenTransactionHandler() {

			@Override
			public void onTransaction(Transaction transaction) {
				final ObjectStore store = transaction.getObjectStore(getRequiredStore());
				
				store.get(organizationId).addCallback(new AsyncCallback<Request>() {

					@Override
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}

					@Override
					public void onSuccess(Request request) {
						final String result;
						
						final JavaScriptObject box = request.getResult();
						
						if(box != null) {
							result = unboxLogo(box);
						} else {
							result = null;
						}
						
						callback.onSuccess(result);
					}
				});
			}
		});
	}
	
	private native JavaScriptObject boxLogo(int organizationId, String logo) /*-{
		return {
			"id": organizationId,
			"logo": logo
		};
	}-*/;
	
	private native String unboxLogo(JavaScriptObject box) /*-{
		return box.logo;
	}-*/;
	
	@Override
	public Store getRequiredStore() {
		return Store.LOGO;
	}
	
}
