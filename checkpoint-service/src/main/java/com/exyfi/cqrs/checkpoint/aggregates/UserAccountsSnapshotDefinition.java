package com.exyfi.cqrs.checkpoint.aggregates;

import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.stereotype.Component;

@Component("checkpointSnapshotDefinition")
public class UserAccountsSnapshotDefinition extends EventCountSnapshotTriggerDefinition {

    public UserAccountsSnapshotDefinition(Snapshotter snapshotter){
        super(snapshotter,10);
    }
}
