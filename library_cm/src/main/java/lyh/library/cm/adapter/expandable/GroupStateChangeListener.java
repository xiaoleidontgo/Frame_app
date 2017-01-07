package lyh.library.cm.adapter.expandable;

/**
 * interface to listen changes in state of sections
 */
public interface GroupStateChangeListener {
    void onSectionStateChanged(Group group, boolean isOpen);
}