package org.nus.trailblaze.models;

import java.util.Date;
import java.util.List;

public class LearningTrail {
    private String id;
    private Date trailDate;
    private String name;
    private Trainer trainer;
    private List<TrailStation> trailStations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTrailDate() {
        return trailDate;
    }

    public void setTrailDate(Date trailDate) {
        this.trailDate = trailDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<TrailStation> getTrailStations() {
        return trailStations;
    }

    public void setTrailStations(List<TrailStation> trailStations) {
        this.trailStations = trailStations;
    }

}
