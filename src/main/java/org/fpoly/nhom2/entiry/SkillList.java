package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the skill_list database table.
 * 
 */
@Entity
@Table(name="skill_list")
@NamedQuery(name="SkillList.findAll", query="SELECT s FROM SkillList s")
public class SkillList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SKILL_LIST_SKILLLISTID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SKILL_LIST_SKILLLISTID_GENERATOR")
	@Column(name="skill_list_id")
	private int skillListId;

	//bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name="profile_id")
	private Profile profile;

	//bi-directional many-to-one association to Skill
	@ManyToOne
	@JoinColumn(name="skill_id")
	private Skill skill;

	public SkillList() {
	}

	public int getSkillListId() {
		return this.skillListId;
	}

	public void setSkillListId(int skillListId) {
		this.skillListId = skillListId;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

}