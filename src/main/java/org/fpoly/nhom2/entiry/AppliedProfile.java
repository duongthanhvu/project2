package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

/**
 * AppliedProfile
 */
@Entity
@Table(name = "applied_profile")
@NamedQuery(name = "AppliedProfile.findAll", query = "SELECT a FROM AppliedProfile a")
public class AppliedProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "APPLIED_PROFILE_APID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLIED_PROFILE_APID_GENERATOR")
    @Column(name = "ap_id")
    private int apId;

    private String cv;

    @Column(name = "cover_letter")
    private String coverLetter;

    @CreationTimestamp
    @Column(name = "date_applied", updatable = false)
    private Timestamp dateApplied;

    // bi-directional many-to-one association to Job
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    // bi-directional many-to-one association to Profile
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public AppliedProfile(){}

    /**
     * @return the apId
     */
    public int getApId() {
        return apId;
    }

    /**
     * @param apId the apId to set
     */
    public void setApId(int apId) {
        this.apId = apId;
    }

    /**
     * @return the coverLetter
     */
    public String getCoverLetter() {
        return coverLetter;
    }
    
    /**
     * @param coverLetter the coverLetter to set
     */
    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    /**
     * @return the cv
     */
    public String getCv() {
        return cv;
    }

    /**
     * @param cv the cv to set
     */
    public void setCv(String cv) {
        this.cv = cv;
    }

    /**
     * @return the dateApplied
     */
    public Timestamp getDateApplied() {
        return dateApplied;
    }

    /**
     * @param dateApplied the dateApplied to set
     */
    public void setDateApplied(Timestamp dateApplied) {
        this.dateApplied = dateApplied;
    }

    /**
     * @return the job
     */
    public Job getJob() {
        return job;
    }

    /**
     * @param job the job to set
     */
    public void setJob(Job job) {
        this.job = job;
    }

    /**
     * @return the profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}