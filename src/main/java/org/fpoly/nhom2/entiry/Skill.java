package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the skill database table.
 * 
 */
@Entity
@NamedQuery(name="Skill.findAll", query="SELECT s FROM Skill s")
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SKILL_SKILLID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SKILL_SKILLID_GENERATOR")
	@Column(name="skill_id")
	private int skillId;

	private String title;

	//bi-directional many-to-one association to SkillList
	@OneToMany(mappedBy="skill")
	private List<SkillList> skillLists;

	public Skill() {
	}

	public int getSkillId() {
		return this.skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<SkillList> getSkillLists() {
		return this.skillLists;
	}

	public void setSkillLists(List<SkillList> skillLists) {
		this.skillLists = skillLists;
	}

	public SkillList addSkillList(SkillList skillList) {
		getSkillLists().add(skillList);
		skillList.setSkill(this);

		return skillList;
	}

	public SkillList removeSkillList(SkillList skillList) {
		getSkillLists().remove(skillList);
		skillList.setSkill(null);

		return skillList;
	}

}