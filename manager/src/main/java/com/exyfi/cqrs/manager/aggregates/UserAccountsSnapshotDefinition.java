package com.exyfi.cqrs.manager.aggregates;

import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.stereotype.Component;

@Component("userSnapshotDefinition")
public class UserAccountsSnapshotDefinition extends EventCountSnapshotTriggerDefinition {

    public UserAccountsSnapshotDefinition(Snapshotter snapshotter){
        super(snapshotter,10);
    }
}
