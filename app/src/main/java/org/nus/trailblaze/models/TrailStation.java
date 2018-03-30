package org.nus.trailblaze.models;

/**
 * Created by plasmashadow on 3/11/18.
 */

public class TrailStation {
    private String id;
    private Location location;
    private String name;
    private String instruction;
    private String sequence;
    private String trail_id;

    public TrailStation() {};

    public TrailStation(String id, Location location, String name, String instruction) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.instruction = instruction;
        this.sequence = sequence;
        this.trail_id = trail_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {  this.name = name; }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getTrailId() {
        return trail_id;
    }

    public void setTrailId(String trail_id) {
        this.trail_id = trail_id;
    }


}
