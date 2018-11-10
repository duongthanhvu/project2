package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * View count
 */
@Entity
@Table(name="view_count")
@NamedQuery(name = "ViewCount.findAll", query = "SELECT v FROM ViewCount v")
public class ViewCount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "VIEWCOUNT_VIEWCOUNTID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VIEWCOUNT_VIEWCOUNTID_GENERATOR")
    @Column(name = "view_count_id")
    private int viewCountId;

    private int count;

    @OneToMany(mappedBy = "viewCount")
    private List<Company> companies;

    @OneToMany(mappedBy = "viewCount")
    private List<Post> posts;

    @OneToMany(mappedBy = "viewCount")
    private List<Job> jobs;

    public ViewCount() {
    }

    /**
     * @return the viewCountId
     */
    public int getViewCountId() {
        return viewCountId;
    }

    /**
     * @param viewCountId the viewCountId to set
     */
    public void setViewCountId(int viewCountId) {
        this.viewCountId = viewCountId;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the companies
     */
    public List<Company> getCompanies() {
        return companies;
    }

    /**
     * @param companies the companies to set
     */
    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    /**
     * @return the posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * @param posts the posts to set
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * @return the jobs
     */
    public List<Job> getJobs() {
        return jobs;
    }

    /**
     * @param jobs the jobs to set
     */
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}