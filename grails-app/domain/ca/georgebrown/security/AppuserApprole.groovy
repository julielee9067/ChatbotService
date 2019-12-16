package ca.georgebrown.security

import org.apache.commons.lang.builder.HashCodeBuilder

class AppuserApprole implements Serializable {

	private static final long serialVersionUID = 1

	Appuser appuser
	Approle approle

	boolean equals(other) {
		if (!(other instanceof AppuserApprole)) {
			return false
		}

		other.appuser?.id == appuser?.id &&
		other.approle?.id == approle?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (appuser) builder.append(appuser.id)
		if (approle) builder.append(approle.id)
		builder.toHashCode()
	}

	static AppuserApprole get(long appuserId, long approleId) {
		AppuserApprole.where {
			appuser == Appuser.load(appuserId) &&
			approle == Approle.load(approleId)
		}.get()
	}

	static boolean exists(long appuserId, long approleId) {
		AppuserApprole.where {
			appuser == Appuser.load(appuserId) &&
			approle == Approle.load(approleId)
		}.count() > 0
	}

	static AppuserApprole create(Appuser appuser, Approle approle, boolean flush = false) {
		def instance = new AppuserApprole(appuser: appuser, approle: approle)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Appuser u, Approle r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = AppuserApprole.where {
			appuser == Appuser.load(u.id) &&
			approle == Approle.load(r.id)
		}.deleteAll()

		if (flush) { AppuserApprole.withSession { it.flush() } }

		rowCount > 0
	}

	static void removeAll(Appuser u, boolean flush = false) {
		if (u == null) return

		AppuserApprole.where {
			appuser == Appuser.load(u.id)
		}.deleteAll()

		if (flush) { AppuserApprole.withSession { it.flush() } }
	}

	static void removeAll(Approle r, boolean flush = false) {
		if (r == null) return

		AppuserApprole.where {
			approle == Approle.load(r.id)
		}.deleteAll()

		if (flush) { AppuserApprole.withSession { it.flush() } }
	}

	static constraints = {
		approle validator: { Approle r, AppuserApprole ur ->
			if (ur.appuser == null) return
			boolean existing = false
			AppuserApprole.withNewSession {
				existing = AppuserApprole.exists(ur.appuser.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['approle', 'appuser']
		version false
	}
}
