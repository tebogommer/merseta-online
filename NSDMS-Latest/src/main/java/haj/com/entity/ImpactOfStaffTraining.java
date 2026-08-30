package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.entity.enums.RatingEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "impact_of_staff_training")
public class ImpactOfStaffTraining implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	/** The train over and above our immediate company needs. */
	@Enumerated
	@Column(name = "train_over_and_above_our_immediate_company_needs")
	private RatingEnum trainOverAndAboveOurImmediateCompanyNeeds;

	/** The train to aid national government priorities such as sips. */
	@Enumerated
	@Column(name = "train_to_aid")
	private RatingEnum trainToAidNationalGovernmentPrioritiesSuchAsSips;

	/** The only train according to our business needs. */
	@Enumerated
	@Column(name = "only_train_according_to_our_business_needs")
	private RatingEnum onlyTrainAccordingToOurBusinessNeeds;

	/** The train to remain globally competitive. */
	@Enumerated
	@Column(name = "train_to_remain_globally_competitive")
	private RatingEnum trainToRemainGloballyCompetitive;

	/** The train improve productivitys. */
	@Enumerated
	@Column(name = "train_improve_productivitys")
	private RatingEnum trainImproveProductivitys;

	/** The we train to keep up with new technologies. */
	@Enumerated
	@Column(name = "we_train_to_keep_up_with_new_technologies")
	private RatingEnum weTrainToKeepUpWithNewTechnologies;

	/** The easier import skills than train the current workforce. */
	@Enumerated
	@Column(name = "easier_import_skills")
	private RatingEnum easierImportSkillsThanTrainTheCurrentWorkforce;

	/** The find that there positions for which need train remain relevant industry. */
	@Enumerated
	@Column(name = "find_that_there_positions")
	private RatingEnum findThatTherePositionsForWhichNeedTrainRemainRelevantIndustry;

	/** The train our staff ensure personal growth line with overall business strategy. */
	@Enumerated
	@Column(name = "train_our_staff")
	private RatingEnum trainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy;

	/** The training current staff proved increase productivity. */
	@Enumerated
	@Column(name = "training_current")
	private RatingEnum trainingCurrentStaffProvedIncreaseProductivity;

	/** The difficult keep up with new technologies our industry. */
	@Enumerated
	@Column(name = "difficult_keep_up")
	private RatingEnum difficultKeepUpWithNewTechnologiesOurIndustry;

	/** The most our learners successfully complete their training the allocated time. */
	@Enumerated
	@Column(name = "most_our_learners")
	private RatingEnum mostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime;

	/** The readily train the unemployed. */
	@Enumerated
	@Column(name = "readily_train_the_unemployed")
	private RatingEnum readilyTrainTheUnemployed;

	/** The there preference for short courses general over full qualifications. */
	@Enumerated
	@Column(name = "there_preference")
	private RatingEnum therePreferenceForShortCoursesGeneralOverFullQualifications;

	/** The training is no substitute for experience our industry. */
	@Enumerated
	@Column(name = "training_is_no")
	private RatingEnum trainingIsNoSubstituteForExperienceOurIndustry;

	/** The readily employ newly trained labour market entrants. */
	@Enumerated
	@Column(name = "readily_employ")
	private RatingEnum readilyEmployNewlyTrainedLabourMarketEntrants;

	/** The we readily employ FET graduates. */
	@Enumerated
	@Column(name = "we_readily_employ_fet_graduates")
	private RatingEnum weReadilyEmployFETGraduates;

	/** The graduates seem getting better and do not require additional training. */
	@Enumerated
	@Column(name = "graduates_seem_getting")
	private RatingEnum graduatesSeemGettingBetterAndDoNotRequireAdditionalTraining;

	/** The training institutions understand industry needs. */
	@Enumerated
	@Column(name = "training_institutions")
	private RatingEnum trainingInstitutionsUnderstandIndustryNeeds;

	/** The work with training institutions ensure quality. */
	@Enumerated
	@Column(name = "work_with_training")
	private RatingEnum workWithTrainingInstitutionsEnsureQuality;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImpactOfStaffTraining other = (ImpactOfStaffTraining) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the train over and above our immediate company needs.
	 *
	 * @return the train over and above our immediate company needs
	 */
	public RatingEnum getTrainOverAndAboveOurImmediateCompanyNeeds() {
		return trainOverAndAboveOurImmediateCompanyNeeds;
	}

	/**
	 * Sets the train over and above our immediate company needs.
	 *
	 * @param trainOverAndAboveOurImmediateCompanyNeeds the new train over and above our immediate company needs
	 */
	public void setTrainOverAndAboveOurImmediateCompanyNeeds(RatingEnum trainOverAndAboveOurImmediateCompanyNeeds) {
		this.trainOverAndAboveOurImmediateCompanyNeeds = trainOverAndAboveOurImmediateCompanyNeeds;
	}

	/**
	 * Gets the train to aid national government priorities such as sips.
	 *
	 * @return the train to aid national government priorities such as sips
	 */
	public RatingEnum getTrainToAidNationalGovernmentPrioritiesSuchAsSips() {
		return trainToAidNationalGovernmentPrioritiesSuchAsSips;
	}

	/**
	 * Sets the train to aid national government priorities such as sips.
	 *
	 * @param trainToAidNationalGovernmentPrioritiesSuchAsSips the new train to aid national government priorities such as sips
	 */
	public void setTrainToAidNationalGovernmentPrioritiesSuchAsSips(
			RatingEnum trainToAidNationalGovernmentPrioritiesSuchAsSips) {
		this.trainToAidNationalGovernmentPrioritiesSuchAsSips = trainToAidNationalGovernmentPrioritiesSuchAsSips;
	}

	/**
	 * Gets the only train according to our business needs.
	 *
	 * @return the only train according to our business needs
	 */
	public RatingEnum getOnlyTrainAccordingToOurBusinessNeeds() {
		return onlyTrainAccordingToOurBusinessNeeds;
	}

	/**
	 * Sets the only train according to our business needs.
	 *
	 * @param onlyTrainAccordingToOurBusinessNeeds the new only train according to our business needs
	 */
	public void setOnlyTrainAccordingToOurBusinessNeeds(RatingEnum onlyTrainAccordingToOurBusinessNeeds) {
		this.onlyTrainAccordingToOurBusinessNeeds = onlyTrainAccordingToOurBusinessNeeds;
	}

	/**
	 * Gets the train to remain globally competitive.
	 *
	 * @return the train to remain globally competitive
	 */
	public RatingEnum getTrainToRemainGloballyCompetitive() {
		return trainToRemainGloballyCompetitive;
	}

	/**
	 * Sets the train to remain globally competitive.
	 *
	 * @param trainToRemainGloballyCompetitive the new train to remain globally competitive
	 */
	public void setTrainToRemainGloballyCompetitive(RatingEnum trainToRemainGloballyCompetitive) {
		this.trainToRemainGloballyCompetitive = trainToRemainGloballyCompetitive;
	}

	/**
	 * Gets the train improve productivitys.
	 *
	 * @return the train improve productivitys
	 */
	public RatingEnum getTrainImproveProductivitys() {
		return trainImproveProductivitys;
	}

	/**
	 * Sets the train improve productivitys.
	 *
	 * @param trainImproveProductivitys the new train improve productivitys
	 */
	public void setTrainImproveProductivitys(RatingEnum trainImproveProductivitys) {
		this.trainImproveProductivitys = trainImproveProductivitys;
	}

	/**
	 * Gets the easier import skills than train the current workforce.
	 *
	 * @return the easier import skills than train the current workforce
	 */
	public RatingEnum getEasierImportSkillsThanTrainTheCurrentWorkforce() {
		return easierImportSkillsThanTrainTheCurrentWorkforce;
	}

	/**
	 * Sets the easier import skills than train the current workforce.
	 *
	 * @param easierImportSkillsThanTrainTheCurrentWorkforce the new easier import skills than train the current workforce
	 */
	public void setEasierImportSkillsThanTrainTheCurrentWorkforce(
			RatingEnum easierImportSkillsThanTrainTheCurrentWorkforce) {
		this.easierImportSkillsThanTrainTheCurrentWorkforce = easierImportSkillsThanTrainTheCurrentWorkforce;
	}

	/**
	 * Gets the find that there positions for which need train remain relevant industry.
	 *
	 * @return the find that there positions for which need train remain relevant industry
	 */
	public RatingEnum getFindThatTherePositionsForWhichNeedTrainRemainRelevantIndustry() {
		return findThatTherePositionsForWhichNeedTrainRemainRelevantIndustry;
	}

	/**
	 * Sets the find that there positions for which need train remain relevant industry.
	 *
	 * @param findThatTherePositionsForWhichNeedTrainRemainRelevantIndustry the new find that there positions for which need train remain relevant industry
	 */
	public void setFindThatTherePositionsForWhichNeedTrainRemainRelevantIndustry(
			RatingEnum findThatTherePositionsForWhichNeedTrainRemainRelevantIndustry) {
		this.findThatTherePositionsForWhichNeedTrainRemainRelevantIndustry = findThatTherePositionsForWhichNeedTrainRemainRelevantIndustry;
	}

	/**
	 * Gets the train our staff ensure personal growth line with overall business strategy.
	 *
	 * @return the train our staff ensure personal growth line with overall business strategy
	 */
	public RatingEnum getTrainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy() {
		return trainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy;
	}

	/**
	 * Sets the train our staff ensure personal growth line with overall business strategy.
	 *
	 * @param trainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy the new train our staff ensure personal growth line with overall business strategy
	 */
	public void setTrainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy(
			RatingEnum trainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy) {
		this.trainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy = trainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy;
	}

	/**
	 * Gets the training current staff proved increase productivity.
	 *
	 * @return the training current staff proved increase productivity
	 */
	public RatingEnum getTrainingCurrentStaffProvedIncreaseProductivity() {
		return trainingCurrentStaffProvedIncreaseProductivity;
	}

	/**
	 * Sets the training current staff proved increase productivity.
	 *
	 * @param trainingCurrentStaffProvedIncreaseProductivity the new training current staff proved increase productivity
	 */
	public void setTrainingCurrentStaffProvedIncreaseProductivity(
			RatingEnum trainingCurrentStaffProvedIncreaseProductivity) {
		this.trainingCurrentStaffProvedIncreaseProductivity = trainingCurrentStaffProvedIncreaseProductivity;
	}

	/**
	 * Gets the difficult keep up with new technologies our industry.
	 *
	 * @return the difficult keep up with new technologies our industry
	 */
	public RatingEnum getDifficultKeepUpWithNewTechnologiesOurIndustry() {
		return difficultKeepUpWithNewTechnologiesOurIndustry;
	}

	/**
	 * Sets the difficult keep up with new technologies our industry.
	 *
	 * @param difficultKeepUpWithNewTechnologiesOurIndustry the new difficult keep up with new technologies our industry
	 */
	public void setDifficultKeepUpWithNewTechnologiesOurIndustry(
			RatingEnum difficultKeepUpWithNewTechnologiesOurIndustry) {
		this.difficultKeepUpWithNewTechnologiesOurIndustry = difficultKeepUpWithNewTechnologiesOurIndustry;
	}

	/**
	 * Gets the most our learners successfully complete their training the allocated time.
	 *
	 * @return the most our learners successfully complete their training the allocated time
	 */
	public RatingEnum getMostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime() {
		return mostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime;
	}

	/**
	 * Sets the most our learners successfully complete their training the allocated time.
	 *
	 * @param mostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime the new most our learners successfully complete their training the allocated time
	 */
	public void setMostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime(
			RatingEnum mostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime) {
		this.mostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime = mostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime;
	}

	/**
	 * Gets the readily train the unemployed.
	 *
	 * @return the readily train the unemployed
	 */
	public RatingEnum getReadilyTrainTheUnemployed() {
		return readilyTrainTheUnemployed;
	}

	/**
	 * Sets the readily train the unemployed.
	 *
	 * @param readilyTrainTheUnemployed the new readily train the unemployed
	 */
	public void setReadilyTrainTheUnemployed(RatingEnum readilyTrainTheUnemployed) {
		this.readilyTrainTheUnemployed = readilyTrainTheUnemployed;
	}

	/**
	 * Gets the there preference for short courses general over full qualifications.
	 *
	 * @return the there preference for short courses general over full qualifications
	 */
	public RatingEnum getTherePreferenceForShortCoursesGeneralOverFullQualifications() {
		return therePreferenceForShortCoursesGeneralOverFullQualifications;
	}

	/**
	 * Sets the there preference for short courses general over full qualifications.
	 *
	 * @param therePreferenceForShortCoursesGeneralOverFullQualifications the new there preference for short courses general over full qualifications
	 */
	public void setTherePreferenceForShortCoursesGeneralOverFullQualifications(
			RatingEnum therePreferenceForShortCoursesGeneralOverFullQualifications) {
		this.therePreferenceForShortCoursesGeneralOverFullQualifications = therePreferenceForShortCoursesGeneralOverFullQualifications;
	}

	/**
	 * Gets the training is no substitute for experience our industry.
	 *
	 * @return the training is no substitute for experience our industry
	 */
	public RatingEnum getTrainingIsNoSubstituteForExperienceOurIndustry() {
		return trainingIsNoSubstituteForExperienceOurIndustry;
	}

	/**
	 * Sets the training is no substitute for experience our industry.
	 *
	 * @param trainingIsNoSubstituteForExperienceOurIndustry the new training is no substitute for experience our industry
	 */
	public void setTrainingIsNoSubstituteForExperienceOurIndustry(
			RatingEnum trainingIsNoSubstituteForExperienceOurIndustry) {
		this.trainingIsNoSubstituteForExperienceOurIndustry = trainingIsNoSubstituteForExperienceOurIndustry;
	}

	/**
	 * Gets the readily employ newly trained labour market entrants.
	 *
	 * @return the readily employ newly trained labour market entrants
	 */
	public RatingEnum getReadilyEmployNewlyTrainedLabourMarketEntrants() {
		return readilyEmployNewlyTrainedLabourMarketEntrants;
	}

	/**
	 * Sets the readily employ newly trained labour market entrants.
	 *
	 * @param readilyEmployNewlyTrainedLabourMarketEntrants the new readily employ newly trained labour market entrants
	 */
	public void setReadilyEmployNewlyTrainedLabourMarketEntrants(
			RatingEnum readilyEmployNewlyTrainedLabourMarketEntrants) {
		this.readilyEmployNewlyTrainedLabourMarketEntrants = readilyEmployNewlyTrainedLabourMarketEntrants;
	}

	/**
	 * Gets the graduates seem getting better and do not require additional training.
	 *
	 * @return the graduates seem getting better and do not require additional training
	 */
	public RatingEnum getGraduatesSeemGettingBetterAndDoNotRequireAdditionalTraining() {
		return graduatesSeemGettingBetterAndDoNotRequireAdditionalTraining;
	}

	/**
	 * Sets the graduates seem getting better and do not require additional training.
	 *
	 * @param graduatesSeemGettingBetterAndDoNotRequireAdditionalTraining the new graduates seem getting better and do not require additional training
	 */
	public void setGraduatesSeemGettingBetterAndDoNotRequireAdditionalTraining(
			RatingEnum graduatesSeemGettingBetterAndDoNotRequireAdditionalTraining) {
		this.graduatesSeemGettingBetterAndDoNotRequireAdditionalTraining = graduatesSeemGettingBetterAndDoNotRequireAdditionalTraining;
	}

	/**
	 * Gets the training institutions understand industry needs.
	 *
	 * @return the training institutions understand industry needs
	 */
	public RatingEnum getTrainingInstitutionsUnderstandIndustryNeeds() {
		return trainingInstitutionsUnderstandIndustryNeeds;
	}

	/**
	 * Sets the training institutions understand industry needs.
	 *
	 * @param trainingInstitutionsUnderstandIndustryNeeds the new training institutions understand industry needs
	 */
	public void setTrainingInstitutionsUnderstandIndustryNeeds(RatingEnum trainingInstitutionsUnderstandIndustryNeeds) {
		this.trainingInstitutionsUnderstandIndustryNeeds = trainingInstitutionsUnderstandIndustryNeeds;
	}

	/**
	 * Gets the work with training institutions ensure quality.
	 *
	 * @return the work with training institutions ensure quality
	 */
	public RatingEnum getWorkWithTrainingInstitutionsEnsureQuality() {
		return workWithTrainingInstitutionsEnsureQuality;
	}

	/**
	 * Sets the work with training institutions ensure quality.
	 *
	 * @param workWithTrainingInstitutionsEnsureQuality the new work with training institutions ensure quality
	 */
	public void setWorkWithTrainingInstitutionsEnsureQuality(RatingEnum workWithTrainingInstitutionsEnsureQuality) {
		this.workWithTrainingInstitutionsEnsureQuality = workWithTrainingInstitutionsEnsureQuality;
	}

	/**
	 * Gets the we readily employ FET graduates.
	 *
	 * @return the we readily employ FET graduates
	 */
	public RatingEnum getWeReadilyEmployFETGraduates() {
		return weReadilyEmployFETGraduates;
	}

	/**
	 * Sets the we readily employ FET graduates.
	 *
	 * @param weReadilyEmployFETGraduates the new we readily employ FET graduates
	 */
	public void setWeReadilyEmployFETGraduates(RatingEnum weReadilyEmployFETGraduates) {
		this.weReadilyEmployFETGraduates = weReadilyEmployFETGraduates;
	}

	/**
	 * Gets the we train to keep up with new technologies.
	 *
	 * @return the we train to keep up with new technologies
	 */
	public RatingEnum getWeTrainToKeepUpWithNewTechnologies() {
		return weTrainToKeepUpWithNewTechnologies;
	}

	/**
	 * Sets the we train to keep up with new technologies.
	 *
	 * @param weTrainToKeepUpWithNewTechnologies the new we train to keep up with new technologies
	 */
	public void setWeTrainToKeepUpWithNewTechnologies(RatingEnum weTrainToKeepUpWithNewTechnologies) {
		this.weTrainToKeepUpWithNewTechnologies = weTrainToKeepUpWithNewTechnologies;
	}
	
	/**
	 * Gets the valid staff training.
	 *
	 * @return the valid staff training
	 */
	public List<String> getValidStaffTraining() {
		List<String> s = new ArrayList<>();
		if (trainOverAndAboveOurImmediateCompanyNeeds == null) {
			s.add("we.train.over.above.immediate.company.needs.is.required");
		}
		if (trainToAidNationalGovernmentPrioritiesSuchAsSips == null) {
			s.add("we.train.national.government.priorities.sips.is.required");
		}
		if (onlyTrainAccordingToOurBusinessNeeds == null) {
			s.add("we.train.according.business.needs.is.required");
		}
		if (trainToRemainGloballyCompetitive == null) {
			s.add("we.train.to.remain.globally.competitive.is.required");
		}
		if (trainImproveProductivitys == null) {
			s.add("we.train.to.improve.productivity.is.required");
		}
		if (weTrainToKeepUpWithNewTechnologies == null) {
			s.add("we.train.to.keep.up.with.new.technologies.is.required");
		}
		if (easierImportSkillsThanTrainTheCurrentWorkforce == null) {
			s.add("it.is.easier.to.import.skills.than.to.train.the.current.workforce.is.required");
		}
		if (findThatTherePositionsForWhichNeedTrainRemainRelevantIndustry == null) {
			s.add("we.find.that.there.positions.which.need.train.remain.relevant.industry.is.required");
		}
		if (trainOurStaffEnsurePersonalGrowthLineWithOverallBusinessStrategy == null) {
			s.add("we.train.staff.ensure.personal.growth.line.with.overall.business.strategy.is.required");
		}
		if (trainingCurrentStaffProvedIncreaseProductivity == null) {
			s.add("training.current.staff.has.proved.increase.productivit.is.required");
		}
		if (difficultKeepUpWithNewTechnologiesOurIndustry == null) {
			s.add("it.is.difficult.keep.up.with.new.technologies.our.industry.is.required");
		}
		if (mostOurLearnersSuccessfullyCompleteTheirTrainingTheAllocatedTime == null) {
			s.add("most.of.our.learners.successfully.complete.their.training.in.the.allocated.time.is.required");
		}
		if (readilyTrainTheUnemployed == null) {
			s.add("we.readily.train.the.unemployed.is.required");
		}
		if (therePreferenceForShortCoursesGeneralOverFullQualifications == null) {
			s.add("there.preference.for.short.courses.general.over.full.qualifications.is.required");
		}
		if (trainingIsNoSubstituteForExperienceOurIndustry == null) {
			s.add("training.no.substitute.for.experience.our.industry.is.required");
		}
		if (readilyEmployNewlyTrainedLabourMarketEntrants == null) {
			s.add("we.readily.employ.newly.trained.labour.market.entrants.is.required");
		}
		if (weReadilyEmployFETGraduates == null) {
			s.add("we.readily.employ.FET.graduates.is.required");
		}
		if (graduatesSeemGettingBetterAndDoNotRequireAdditionalTraining == null) {
			s.add("graduates.seem.getting.better.and.do.not.require.additional.training.is.required");
		}
		if (trainingInstitutionsUnderstandIndustryNeeds == null) {
			s.add("training.institutions.understand.industry.needs.is.required");
		}
		if (workWithTrainingInstitutionsEnsureQuality == null) {
			s.add("we.work.with.training.institutions.to.ensure.quality.is.required");
		}
		
		
		
		return s;
	}

}
