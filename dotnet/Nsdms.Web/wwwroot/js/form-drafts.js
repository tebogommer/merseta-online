// merSETA NSDMS - Client-Side Form Draft Resilience & Auto-Save
window.NsdmsDrafts = {
    prefix: "nsdms_form_draft_",

    saveDraft: function (key, draftJson) {
        try {
            if (!window.localStorage) return false;
            var storageKey = this.prefix + key;
            window.localStorage.setItem(storageKey, draftJson);
            return true;
        } catch (e) {
            console.warn("NSDMS Drafts: Unable to write to localStorage (quota exceeded or disabled).", e);
            return false;
        }
    },

    getDraft: function (key) {
        try {
            if (!window.localStorage) return null;
            var storageKey = this.prefix + key;
            return window.localStorage.getItem(storageKey);
        } catch (e) {
            console.warn("NSDMS Drafts: Unable to read from localStorage.", e);
            return null;
        }
    },

    removeDraft: function (key) {
        try {
            if (!window.localStorage) return false;
            var storageKey = this.prefix + key;
            window.localStorage.removeItem(storageKey);
            return true;
        } catch (e) {
            return false;
        }
    },

    listDrafts: function () {
        try {
            if (!window.localStorage) return [];
            var drafts = [];
            for (var i = 0; i < window.localStorage.length; i++) {
                var k = window.localStorage.key(i);
                if (k && k.indexOf(this.prefix) === 0) {
                    var val = window.localStorage.getItem(k);
                    if (val) {
                        try {
                            drafts.push(JSON.parse(val));
                        } catch (err) {
                            // ignore unparseable
                        }
                    }
                }
            }
            return drafts;
        } catch (e) {
            return [];
        }
    },

    clearExpiredDrafts: function () {
        try {
            if (!window.localStorage) return;
            var now = new Date().toISOString();
            var keysToRemove = [];
            for (var i = 0; i < window.localStorage.length; i++) {
                var k = window.localStorage.key(i);
                if (k && k.indexOf(this.prefix) === 0) {
                    var val = window.localStorage.getItem(k);
                    if (val) {
                        try {
                            var parsed = JSON.parse(val);
                            if (parsed.expiresAt && parsed.expiresAt < now) {
                                keysToRemove.push(k);
                            }
                        } catch (e) {
                            // ignore
                        }
                    }
                }
            }
            keysToRemove.forEach(function (k) { window.localStorage.removeItem(k); });
        } catch (e) {
            // ignore
        }
    }
};
