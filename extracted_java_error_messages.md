# Extracted Java Error Messages (Enhanced)

This table contains deduplicated error messages extracted from the Java codebase, including the inferred flow (Class and Method) and reason/condition (simple English business logic rules).

| Error Message | Exception Type | File/Flow | Reason / Condition |
|---------------|----------------|-----------|--------------------|
| ` Provider Certificate is not available for this application` | `Exception` | `TrainingProviderApplicationService.java -> downloadProviderCertificate()` | Triggered when: Tp application get certificate number( |
| `A merSETA employee cannot be registered as a Mentor` | `Exception` | `SitesSmeUI.java -> searchForUser()` | Triggered when: Company employees service finding by user count sme user id( |
| `Accreditation Number is required` | `Exception` | `EmployeesImportService.java -> doPivotalNonPivotalChecks()` | Triggered when: Is blank: ei get accreditation number( |
| `Additional Document Detail Notes` | `Exception` | `QdfCompanyUI.java -> storeNewFileRequired()` | Triggered when: This doc get note( |
| `Address Not Configured/Set. Contact Support!` | `Exception` | `WorkPlaceApprovalService.java -> locateCrmForTask()` | Triggered when: Company residential address (+1 other variants (and other variations) |
| `Already applied for completion letter` | `Exception` | `LearnersUI.java -> requestCompletionLetter()` | Triggered when: Completion letter service finding by company learner completion letter get company learners( |
| `Already applied for non seta qualifications completion` | `Exception` | `LearnersUI.java -> requestNonSetaQualificationsCompletion()` | Triggered when: Non seta qualifications completion service finding by company learner non seta qualifications completion get company learners( |
| `Already applied for verification` | `Exception` | `LearnersUI.java -> requestVerificationLearner() (+1 others)` | Triggered when: Training provider verfication service finding by company learner training provider monitoring company learners (+1 other variants (and other variations) |
| `Already shut down` | `IllegalStateException` | `WatcherServiceReloadingTrigger.java -> start()` | Triggered when: Executor service is shutdown( |
| `An OFO or Specialisation code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get ofo code( |
| `An attempt to create Site Visit task faild, because we are unable to locate CLO` | `Exception` | `CompanyLearnersService.java -> learnerRejection()` | Triggered when: Clo is provided and clo id( |
| `Another user is already registered with the given RSA ID number` | `Exception` | `UsersService.java -> updateUserValidation()` | Triggered when: U is provided and u id( |
| `Another user is already registered with the given details` | `Exception` | `RegisterService.java -> checkUserException()` | Triggered when: Dao get user by email id number passport number(u get email( |
| `Another user is already registered with the given email` | `Exception` | `UsersService.java -> updateUserValidation()` | Triggered when: U is provided and u id( |
| `Another user is already registered with the given passport number` | `Exception` | `UsersService.java -> updateUserValidation()` | Triggered when: U is provided and u id( |
| `Application already added to the list` | `Exception` | `GenerateCertificatesUI.java -> precheckIfExists() (+1 others)` | Triggered when: Exits |
| `Assessor and Moderator must not be the same` | `Exception` | `MonitorVerificationLearnersUI.java -> precheckLegacyAssMod() (+1 others)` | Triggered when: Summative assessment report unit standards get legacy assessor accreditation( |
| `Assessor and moderator must not be the same for the assessed unit standard` | `Exception` | `VerificationLearnersUI.java -> addModeratorSingle() (+1 others)` | Triggered when: Summative assessment report unit standards assessor application (+3 other variants (and other variations) |
| `Assessor and moderator must not be the same user` | `Exception` | `VerificationLearnersUI.java -> applyToAllAssessors() (+1 others)` | Triggered when: Sarus assessor application (+3 other variants (and other variations) |
| `Assessor/Moderator Error` | `Exception` | `MonitorLegacyAssessorModUI.java -> updateUnitStandards()` | Triggered when: Assessor moderator application is missing |
| `Assign a property before proceeding.` | `Exception` | `ReportGenerationPropertiesService.java -> checkIfPropertyUsed()` | Triggered when: Report generation properties get report property( |
| `Assignment Error! You can not assign current user as they are assigned as the assessor. Please select a different user to assign as the moderator. ` | `Exception` | `CompanyLearnersTradeTestService.java -> assignNewAssessorModeratorToTradeTest()` | Triggered when: Failed validiation |
| `Assignment Error! You can not assign current user as they are assigned as the moderator. Please select a different user to assign as the assessor. ` | `Exception` | `CompanyLearnersTradeTestService.java -> assignNewAssessorModeratorToTradeTest()` | Triggered when: Failed validiation |
| `Bank account number can not contain the following: characters / letters, underscores, hyphens and spaces. Only numbers are accepted.` | `Exception` | `BankingDetailsService.java -> bankingDetailsValidiation()` | Triggered when: Entity get bank acc number( |
| `Bank branch code can not contain the following: characters / letters, underscores, hyphens and spaces. Only numbers are accepted.` | `Exception` | `BankingDetailsService.java -> bankingDetailsValidiation()` | Triggered when: Entity get branch code( |
| `Can't allocate, please ensure verification and 		// grant application are both approved.` | `Exception` | `DgAllocationService.java -> checkForAllocationWithException()` | Triggered when: Wsp approved and dg verification approved |
| `Can't allocate, please ensure verification and grant application are both approved.` | `Exception` | `DgAllocationService.java -> checkForAllocationWithException()` | Triggered when: Wsp approved and dg verification approved |
| `Certificate is not avaialable for this application` | `Exception` | `AssessorModeratorApplicationService.java -> downloadAssessorModCertificateLetter()` | Triggered when: Am get certificate number( |
| `Code must be unique` | `Exception` | `GenderService.java -> create() (+54 others)` | Triggered when: Dao find unique code(entity |
| `Code must be unique.` | `Exception` | `CitizenResidentStatusService.java -> create()` | Triggered when: Dao find unique code(entity |
| `Company Registration Number Has Been Located On NSDMS. Please Enter a Differenet Company Registration Number or Search Via: Levy-Paying or Non-Levy Paying Entity Selection.` | `Exception` | `SearchCompanyUI.java -> findForNewRegNumber()` | Triggered when: Company is missing |
| `Company Registration Number aready exist in merSETA database` | `Exception` | `TrainingProviderUI.java -> levyNumberRegnumberValidation()` | Triggered when: Comp list is provided and comp list size( |
| `Company cannot be null` | `Exception` | `LegacyCompanyLearnersService.java -> createLearner()` | Triggered when: Training provider id (+1 other variants (and other variations) |
| `Company did not pay the last 12 months levies` | `CategorizationException` | `CategorisationService.java -> paidLast12MonthsLevy()` | Triggered when: No levies < 12 |
| `Company does not exist` | `Exception` | `TestDownloadUI.java -> generateImpementationPlan()` | Triggered when: Wsp list is provided and wsp list size( |
| `Company does not have organisation type` | `Exception` | `LearnerRegistrationFormOtpSignoffUI.java -> continueRegistration() (+1 others)` | Triggered when: This company get organisation type( |
| `Company not yet registered.` | `Exception` | `SiteVisitReportService.java -> createOnceRejected() (+1 others)` | Triggered when: Entity get company( |
| `Company registartion number already in use, please provide a different registartion number or contact support!` | `Exception` | `CompanyService.java -> validateRegistartionNumberByCompany()` | Triggered when: Companies found is greater than zero |
| `Configuration error. You have no roles assigned. Please contact support` | `Exception` | `UsersRoleService.java -> findUniqueRoles()` | Triggered when: L size( |
| `Contact person already added` | `Exception` | `TrainingProviderApplicationUI.java -> addContactValidation()` | Triggered when: Cp get user( |
| `Contact person already added to the list` | `Exception` | `TrainingProviderUI.java -> addContactPerson()` | Triggered when: Add users validation (+1 other variants (and other variations) |
| `Could not find sheet!` | `Exception` | `XLSCommon.java -> getSheet()` | Triggered when: Name trim( |
| `Could not set up WatcherService for config file reloads` | `IllegalStateException` | `WatcherServiceReloadingTrigger.java -> Unknown Method()` | A technical backend error occurred during processing |
| `Data Already Generated for Quarter Selected` | `Exception` | `QmrFinYearsService.java -> generateDataForQmrFinYear()` | Triggered when: Not update list is empty( |
| `Date Range Incorrect` | `ValidationException` | `ReportDataService.java -> findByAgeRangeWspPivotNonPivot() (+1 others)` | Triggered when: From age > to age |
| `Date of Birth is missing` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Ei get date of birth( |
| `Date of moderation cannot be before assessment date` | `Exception` | `VerificationLearnersUI.java -> addAssessmentDate() (+1 others)` | Triggered when: Summative assessment report unit standards get moderation date( |
| `Date passed for extension request. Please contact support.` | `Exception` | `ExtensionRequestUI.java -> extensionrequestInsertVersionTwo()` | Triggered when: Today after( generic utility get start of day(date format parse("01 05 " + integer parse int( generic utility sdf year format(new date (+2 other variants (and other variations) |
| `Department and Parent Department cant be the same` | `Exception` | `HostingCompanyDepartmentsService.java -> create()` | Triggered when: Entity get parent department( |
| `Designation Already Assigned, Select Different Designation Before Proceeding` | `Exception` | `SdpTypeService.java -> validiateDesignationUsed()` | Triggered when: Count by designation id(entity designation (+1 other variants (and other variations) |
| `Designation already assigned to another SDP contact, Please select a different designation.` | `Exception` | `PrimarySdpRelinkUI.java -> submitUpdateDesignationSdp() (+1 others)` | Triggered when: Counter if new designation assigned is greater than zero |
| `Designation already underway in approval process. Please select a different designation.` | `Exception` | `PrimarySdpRelinkUI.java -> submitUpdateDesignationSdp() (+1 others)` | Triggered when: Counter in workflow is greater than zero |
| `Disability already exist on your disability list` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> usersDisabilityPreCheck() (+10 others)` | Triggered when: Ud is provided and ud get disability status( |
| `Disability code is invalid` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Disability status service finding by code ei get disability( |
| `Documents Outstanding, Unable To Proceed` | `Exception` | `TrainingProviderMonitoringService.java -> updateEditAndValidiate()` | Triggered when: Auditor monitor review get evidence required( |
| `ETQA code is invalid` | `Exception` | `EmployeesImportService.java -> doPivotalNonPivotalChecks()` | Triggered when: Is blank: ei get etqa( |
| `Email address provided is already registered on the application. Provide a new email address or contact merSETA support.` | `Exception` | `PrimarySdpRelinkUI.java -> doneUserBit() (+1 others)` | Triggered when: Users service count of users by email form user get email( |
| `Email already in use on the system.` | `ValidationException` | `UsersService.java -> isMailUsed()` | Triggered when: U is provided |
| `Email already in use, please provide a different email` | `Exception` | `UsersService.java -> validateEmailAddressByUser()` | Triggered when: Email found is greater than zero |
| `Employees cannot be assigned.` | `Exception` | `TrainingProviderUI.java -> callBackMethod()` | Triggered when: Hosting company employees service finding by user count facilitator assessor id (+2 other variants (and other variations) |
| `Employees cannot register as an Assessor or Moderator` | `Exception` | `AssessorModeratorApplicationService.java -> createAssessorLegacyAMApplicationAndSendTask()` | Triggered when: Company employees service finding by user count form user id( |
| `Employees cannot register as an Assessor/Moderator.` | `Exception` | `AssesorModiratorUI.java -> callBackMethod()` | Triggered when: Company employees service finding by user count form user id( |
| `Employees cannot register companies or register as an SDF.` | `Exception` | `CompanyService.java -> callBackMethod() (+2 others)` | Triggered when: Company employees service finding by user count form user id( |
| `Employer Sdl error when registering learner, please contact the merSETA Office` | `Exception` | `LegacyLearnersEmployeesUI.java -> redirectLegacyexperiential_vh()` | Triggered when: Legacyexperiential get employer sdl( |
| `Employer error on registration` | `Exception` | `EmployeeCompanyLearnersService.java -> createLearner() (+4 others)` | Triggered when: Company is missing ) (+1 other variants (and other variations) |
| `Employer error, Please contact you administrator` | `Exception` | `CompanyLearnersService.java -> createNewLearnerBySDP() (+1 others)` | Triggered when: Cl employer (+1 other variants (and other variations) |
| `Employer is not yet active !!!, please activate employer before proceeding` | `Exception` | `LegacyLearnersUI.java -> redirectLegacySectTwentyEight() (+2 others)` | Triggered when: Selected company company status (+1 other variants (and other variations) |
| `Employer is not yet, please activate company before proceeding` | `Exception` | `LegacyLearnersUI.java -> redirectLegacySectTwentyEight() (+1 others)` | Triggered when: Provider get company status( |
| `Employment Type code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get employment type code( |
| `Employment type code is invalid` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Employment type service finding by code ei get employment type( |
| `End date is invalid` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVData()` | Triggered when: A get start date( |
| `End date is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataOnUpdate()` | Triggered when: A get end date( |
| `Enrolment Status code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get enrolment status code( |
| `Enter either SDL or Site number` | `Exception` | `EmployeesHistoryService.java -> validate() (+1 others)` | Triggered when: Is blank: emp get sdl number( |
| `Equity code is invalid` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Equity service finding by code ei get equity( |
| `Equity code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get equity code( |
| `Error` | `Exception` | `MonitorVerificationLearnersUI.java -> linkUnitStandard()` | Triggered when: List summative assessment report unit standards count (+2 other variants (and other variations) |
| `Error On Generating Certificate, Please contact your administrator` | `Exception` | `TrainingProviderVerficationService.java -> generateAndZipDocuments()` | Triggered when: Companylearners get intervention type( |
| `Error Please Contact Administrator` | `Exception` | `WorkPlaceApprovalService.java -> rejectWorkPlaceApprovalWithdrawal()` | Triggered when: Create user is provided |
| `Error locating questions for the survey. Contact Support!` | `Exception` | `WorkplaceMonitoringLearnerSurveyService.java -> validiateInformation()` | Triggered when: Question get answer( |
| `Error on registration, please contact your administrator` | `Exception` | `CompanyInfoUI.java -> prepLegacyEmployerWA2UnitStandardRequest()` | Triggered when: Qualification is missing ) (+3 other variants (and other variations) |
| `Error when approving` | `Exception` | `CompanyLearnersService.java -> completeCompanyLearnersForReview() (+2 others)` | General system failure or invalid processing state |
| `Error when downloading form` | `Exception` | `LearnerRegistrationFormUI.java -> downloadForm() (+7 others)` | Triggered when: Companylearners is provided ) (+3 other variants (and other variations) |
| `Error when retrieving training provider, please contact merSETA administrator` | `Exception` | `LearnerRegistrationFormUI.java -> continueValidateSdpAccreditaion() (+3 others)` | General system failure or invalid processing state |
| `Error with the laerner application` | `Exception` | `EmployeesLearnersRegistrationUI.java -> requestVerificationLearner()` | Triggered when: Training provider verfication get company learners( |
| `Error with the legacy internship` | `Exception` | `LegacyInternshipUI.java -> prepareLegacyInternship()` | Triggered when: Legacyinternship is provided and legacyinternship id( |
| `Error with the legacy learnership` | `Exception` | `LegacySectionTwentyEightTradeTestUI.java -> prepareLegacyTvet() (+7 others)` | Triggered when: Legacyapprenticeship is provided and legacyapprenticeship id (+6 other variants (and other variations) |
| `Error with the verification application` | `Exception` | `EmployeesLearnersRegistrationUI.java -> requestVerificationLearner()` | Triggered when: Training provider verfication is missing |
| `Estimated Cost of training is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get estimated cost( |
| `Exceeded Batch Size Limit. Please Upload a CSV containing No More Than 20 000 Entries.` | `Exception` | `AtrUI.java -> handleFileUpload() (+1 others)` | A technical backend error occurred during processing |
| `Extension cannot be granted for learner who completed Level 1 or Level 2` | `Exception` | `CompanyLearnersService.java -> calculateNewTerminationDate()` | Triggered when: Company learners lost time get company learners( |
| `FAIL SAFE: Allocation Data Against Grant Application. Unable to proceed!` | `Exception` | `DgVerificationService.java -> reopenDgVerification()` | Triggered when: Dgap service count of by wsp id dg verification get wsp( |
| `Failed to add Unit Standard  beacause there is an active application for the Unit Standard selected` | `Exception` | `AssesorModiratorUI.java -> validateUserUS()` | Triggered when: User user get unit standard( |
| `Failed to add qualification beacause there is an active application for the qualification selected` | `Exception` | `AssesorModiratorUI.java -> validateUserQual()` | Triggered when: User qal get qualification( |
| `First Name is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get first name( |
| `First Name missing` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Is blank: ei get first name( |
| `From dates need to be supplied` | `Exception` | `ReconByDateUI.java -> validateDates()` | Triggered when: From date is missing \|\| from date inv is missing |
| `Funding code is invalid` | `Exception` | `EmployeesImportService.java -> doPivotalNonPivotalChecks()` | Triggered when: Not is blank: ei get source of funding( |
| `Funding code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get funding code( |
| `GP Vendor is curently not populated, contact support with Levy Number.` | `Exception` | `BankingDetailsUI.java -> finalApproveTask()` | Triggered when: Vendor is missing |
| `Gender code is invalid` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Gender service finding by code ei get gender( |
| `Gender code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get gender code( |
| `Generation Underway, please wait till previous generation complete or contact support.` | `Exception` | `MandatoryGrantsTransactionGpUI.java -> generateNewBatch()` | Triggered when: Gen underway and not can gen |
| `Hosting company setup error. No process roles assigned to Hosting company.` | `Exception` | `TasksService.java -> findFirstInProcessAndCreateTask()` | Triggered when: User list is missing \|\| user list count (+1 other variants (and other variations) |
| `ID Number not valid` | `Exception` | `AtrUI.java -> setDOBGenderNationality() (+1 others)` | Triggered when: Generic utility check rsa id(mandatory grant id number( |
| `ID type code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A id type code( |
| `IP address is empty` | `Exception` | `ResolveIPService.java -> sendGet()` | Triggered when: Is empty: ip address |
| `Instantiate.amount.bean` | `ValidationException` | `CurrencyService.java -> convert()` | Triggered when: Amount bean is missing |
| `Intervention Title Code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get intervention title( |
| `Intervention Type code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get intervention type code( |
| `Intervention level code is invalid` | `Exception` | `EmployeesImportService.java -> doPivotalNonPivotalChecks()` | Triggered when: Not is blank: ei get intervention level( |
| `Intervention types can not be the same. Select a different Intervention type for one of the selection.` | `Exception` | `WorkplaceMonitoringLearnerSurveyAnswersUI.java -> copyInterventionTypeQuestions()` | Triggered when: Selected intervention type answers id( |
| `Invalid Disability Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get disability( |
| `Invalid Employment Status Code` | `Exception` | `MandatoryGrantService.java -> validateCSVData()` | Triggered when: A get employment status( |
| `Invalid Employment Type Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get employment type( |
| `Invalid Enrolment Status Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get enrolment status( |
| `Invalid Equity Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get equity( |
| `Invalid Funding Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo() (+1 others)` | Triggered when: A get funding( |
| `Invalid Gender Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get gender( |
| `Invalid ID type code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A id type( |
| `Invalid Intervention Title Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get non credit bearing intervation title( |
| `Invalid Intervention Type Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo() (+1 others)` | Triggered when: A get intervention type( |
| `Invalid Municipality Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get municipality( |
| `Invalid Nationality Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get nationality( |
| `Invalid OFO Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo() (+1 others)` | Triggered when: A get ofo codes( |
| `Invalid OFO Specialisation Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get ofo codes( |
| `Invalid Provider Type Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo() (+1 others)` | Triggered when: A get provider type( |
| `Invalid Qualification Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo() (+1 others)` | Triggered when: A get qualification( |
| `Invalid Skills Program Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get skills program( |
| `Invalid Skills Set Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get skills set( |
| `Invalid Training Delivery Method Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo() (+1 others)` | Triggered when: A get training delivery method( |
| `Invalid Unit Standard Code` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get unit standard( |
| `Invalid date/time. The end date/time must be after the start date/time.` | `Exception` | `ReviewCommitteeMeetingUI.java -> validateStartAndEnddate()` | Triggered when: Event get start date( |
| `Invalid expiriy date, Your expiriy date must be greater than your start date` | `Exception` | `TrainingProviderUI.java -> validateStartAndEndDate()` | Triggered when: Days < 1 |
| `Invoice date is not valid` | `ValidationException` | `CreatePayablesDocumentAdapter.java -> createPayablesDocumentFromBatchEntry()` | A technical backend error occurred during processing |
| `Kindly be advised that you require the relevant authorisation to access this information.` | `Exception` | `LearnersUI.java -> validiateCanViewInformation() (+2 others)` | Triggered when: Sdp type is missing \|\| sdp type get view learners( |
| `Language already exist on your language list` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> languagePreCheck() (+12 others)` | Triggered when: Ul is provided and ul get language( |
| `Last Name is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get last name( |
| `Last Name missing` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Is blank: ei get last name( |
| `Latitude Degrees may not be greater than -22` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get latitude degrees( |
| `Latitude Degrees may not have a value less than -35` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get latitude degrees( |
| `Latitude Degrees must be a negative value` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get latitude degrees( |
| `Latitude Minutes may only contain whole numbers` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get latitude minutes( |
| `Latitude Minutes must have a length of exactly 2 (leading zeros) and may not be greater than 59` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Count not = 2 \|\| tp residential address get latitude minutes( |
| `Latitude Seconds may not be greater than 59.999` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get latitude seconds( |
| `Latitude Seconds may only contain characters 1234567890` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get latitude seconds( |
| `Latitude Seconds must have a length of exactly 6 (nn.nnn)` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: String value of(tp residential address get latitude seconds( |
| `Learner already accredited for the requested unit standard ` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get company learners( |
| `Learner already accredited for the requested unit standard Skills Set` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get company learners( |
| `Learner already added to the list` | `Exception` | `VerificationLearnersUI.java -> addToList()` | Triggered when: Check if alreadyadded( |
| `Learner already registered for the requested Skills Programme` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get company learners( |
| `Learner already registered for the requested qualification` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get company learners( |
| `Learner already registered with company` | `ValidationException` | `CompanyUsersService.java -> createCompanyUsersCheckExistence()` | Triggered when: Cu is missing |
| `Learner cueently underway with an ARPL. Please complete open ARPL with learner before proceeding.` | `Exception` | `CompanyLearnersTradeTestService.java -> validateUserAllowedArpl()` | Triggered when: Count open by arpl by user(learner, trade test type enum arpl |
| `Learner does not exist!!! Please load the learner first` | `Exception` | `LearnerRegistrationFormOtpSignoffUI.java -> callBackMethod() (+1 others)` | Triggered when: This user id( |
| `Learner error` | `Exception` | `LearnersUI.java -> viewCompanyLearnerDetails() (+1 others)` | Triggered when: Company learners is missing |
| `Learner has already obtained qualification under ARPL. Please select a different qualification to proceed.` | `Exception` | `CompanyLearnersTradeTestService.java -> validateUserAllowedArpl()` | Triggered when: Count company learners trade test by type status qualification and learner id( trade test type enum arpl, approval enum qualification obtained, qualification ge |
| `Learner is already registered for this qualification` | `Exception` | `CompanyLearnersService.java -> checkIfRegisteredForQualification() (+2 others)` | Triggered when: List size( |
| `Learner is currently underway / obtained qualification through the NSDMS learner module. Please select a different qualification to proceed.` | `Exception` | `CompanyLearnersTradeTestService.java -> validateUserAllowedArpl()` | Triggered when: Company learners service count of company learners by learner id qualification and not learner status learner id( |
| `Learner is not kinked to an employer , please contact the merSETA Office` | `Exception` | `LegacyLearnersUI.java -> redirectLegacySectTwentyEight() (+2 others)` | Triggered when: Legacyunitstandard employer sdl (+9 other variants (and other variations) |
| `Learner is not kinked to an training provider, please contact the merSETA Office` | `Exception` | `LegacyLearnersUI.java -> redirectLegacyUnitStandard()` | Triggered when: Legacyskillsprogramme accreditation number (+3 other variants (and other variations) |
| `Learner is not linked to a training provider, please contact the merSETA Office` | `Exception` | `LegacyLearnersEmployeesUI.java -> redirectLegacyUnitStandard_vh()` | Triggered when: Legacyunitstandard get accreditation number( |
| `Learner is not linked to an employer , please contact the merSETA Office` | `Exception` | `LegacyLearnersEmployeesUI.java -> redirectLegacySkillsprogramme_vh()` | Triggered when: Legacylearnership employer sdl (+3 other variants (and other variations) |
| `Learner is not linked to an employer, please contact the merSETA Office` | `Exception` | `LegacyLearnersUI.java -> runInit() (+3 others)` | Triggered when: Legacytvet employer sdl (+3 other variants (and other variations) |
| `Learner is not linked to an training provider, please contact the merSETA Office` | `Exception` | `LegacyLearnersEmployeesUI.java -> redirectLegacyUnitStandard_vh()` | Triggered when: Legacyskillsprogramme accreditation number (+2 other variants (and other variations) |
| `Learner is not linked to this company !!! Please link the learner first` | `Exception` | `LearnerRegistrationFormOtpSignoffUI.java -> callBackMethod() (+1 others)` | Triggered when: Company learner users is missing |
| `Learner is not not yet active!!!` | `Exception` | `LearnerRegistrationFormOtpSignoffUI.java -> callBackMethod() (+1 others)` | Triggered when: This user get active( |
| `Learners do not have same intervention types` | `Exception` | `VerificationLearnersUI.java -> addToList()` | Triggered when: Not check if same intervention( |
| `Learners do not have same qualification` | `Exception` | `VerificationLearnersUI.java -> addToList()` | Triggered when: Not check if same qualification( |
| `Learnership not found` | `Exception` | `LegacyLearnersUI.java -> runInit() (+3 others)` | Triggered when: Qualification is missing ) (+1 other variants (and other variations) |
| `Learnership not the same` | `Exception` | `VerificationLearnersUI.java -> checkIfSameQualification()` | Triggered when: Trainingproviderverfication get company learners( |
| `Levy number already in use, please provide a different levy number or contact support!` | `Exception` | `CompanyService.java -> validateLevyNumberByCompany()` | Triggered when: Companies found is greater than zero |
| `Longitude Degree may only contain whole numbers` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get longitude degrees( |
| `Longitude Degrees may not be greater than 33 and may not have a value less than 16` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get longitude degrees( |
| `Longitude Minutes may only contain whole numbers` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get longitude minutes( |
| `Longitude Minutes must have a length of exactly 2 (leading zeros) and may not be greater than 59` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Count not = 2 \|\| tp residential address get longitude minutes( |
| `Longitude Seconds may only contain characters 1234567890` | `Exception` | `TrainingProviderUI.java -> validatelatitudeminutes()` | Triggered when: Tp residential address get latitude seconds( |
| `Lost day(s) cannot be less than 1` | `Exception` | `CompanyLearnersService.java -> calculateNewTerminationDate()` | Triggered when: Lost days < 1 |
| `MOA found in batch processing. Please withdraw MOA there.` | `Exception` | `ActiveContractsService.java -> checkIfCanWithDrawApplication()` | Triggered when: Dg contracting bulk items service count of by active contract id active contracts id( |
| `Maximum days cannot be less than 6` | `Exception` | `CompanyLearnersService.java -> calculateNewTerminationDate()` | Triggered when: Lost days < 6 |
| `Maximum days cannot be more than 30` | `Exception` | `CompanyLearnersService.java -> calculateNewTerminationDate()` | Triggered when: Lost days > 30 |
| `Maximum days cannot be more than 5` | `Exception` | `CompanyLearnersService.java -> calculateNewTerminationDate()` | Triggered when: Lost days > 5 |
| `Mentor already being rejected` | `Exception` | `DataFixService.java -> fixSitesSme()` | Triggered when: All task sites sme is provided and all task sites sme size( |
| `Mentor with that ID is Not avalible` | `Exception` | `DataFixService.java -> fixSitesSme()` | Triggered when: All task sites sme is provided and all task sites sme size( |
| `MenuItem must be inside a form element` | `FacesException` | `UltimaMenuRenderer.java -> encodeMenuItem()` | Triggered when: Form is missing |
| `MerSETA Employee Information can not be used here!` | `Exception` | `SearchUserPassportOrIdUI.java -> findUserByIDOrPassportExcludeEmployeeUsers()` | Triggered when: Hosting company employees service finding by user count user id( |
| `Minimum credits not met` | `Exception` | `MonitorVerificationLearnersUI.java -> completeWorkflowToQualityAssuror() (+2 others)` | Triggered when: Service check fieldsprovided(trainingproviderverfication) (+2 other variants (and other variations) |
| `Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character` | `Exception` | `GenericUtility.java -> checkPassword()` | Triggered when: Not m find( |
| `Missing Employee ID` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Is blank: ei get employee id( |
| `Missing ID Type` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Is blank: ei id type( |
| `Missing SDL Number or Site number` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Is blank: ei get sdl number( |
| `Moderation date cannot be after assessment date` | `Exception` | `VerificationLearnersHoldingUI.java -> saveUnitStandards() (+2 others)` | Triggered when: Summative assessment report unit standards get assesment date( |
| `Municipality Code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get municipality code( |
| `Municipality code is invalid` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Municipality service finding by code ei get municipality( |
| `Nationality code is invalid` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Nationality service finding by code ei get nationality( |
| `Nationality code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get nationality code( |
| `Need company details to run this report` | `Exception` | `TasksService.java -> findTaskBySDFCompanyforCompany()` | Triggered when: Company is missing \|\| company id( |
| `Need sdf details to run this report` | `Exception` | `TasksService.java -> findTaskBySDFCompanyforUser()` | Triggered when: User is missing \|\| user id( |
| `No Approved Primary SDP Contact Person Assigned.` | `Exception` | `PrimarySdpDelinkUI.java -> searchTrainingProviderApplication()` | Triggered when: Sdp company service count of sdp type by holding company and approval status company id (+1 other variants (and other variations) |
| `No Assessor legacy data found for the ID number provided` | `Exception` | `AssesorModiratorUI.java -> callBackMethod()` | Triggered when: Form user get postal address( |
| `No Assessors Assigned To Test Centre. Please Assign Assessors Before Proceeding.` | `Exception` | `LegacyTradeTestRegistrationUI.java -> prepAssessorSelectionList() (+2 others)` | Triggered when: Company user selection list is empty( |
| `No CLO assigned for region` | `Exception` | `MgVerificationService.java -> uploadMGVerificationEvidence()` | Triggered when: Get clo(entity get wsp( |
| `No CLO assigned to the company region` | `Exception` | `WorkPlaceApprovalService.java -> completeLegacyRegistration()` | Triggered when: User list size( |
| `No CRM assigned for region` | `Exception` | `MgVerificationService.java -> cloCrmReviewMgVerificationEvidence()` | Triggered when: Get crm(entity get wsp( |
| `No CRM found, please contact you administrator` | `Exception` | `WorkPlaceApprovalService.java -> approveWorkPlaceApprovalWithdrawal()` | Triggered when: U is missing |
| `No Client Laison Office Assigned` | `Exception` | `ActiveContractsService.java -> completeWorkflowProjectTerminationSDF()` | Triggered when: Entity get clo( |
| `No Client Liaison Office Assigned To Company Region` | `Exception` | `WorkPlaceApprovalService.java -> witdrawWorkPlaceApproval()` | Triggered when: User is missing ) (+1 other variants (and other variations) |
| `No Client Service Administrator assigned to region` | `Exception` | `EmployeeCompanyLearnersService.java -> completeCompanyLearnersOld() (+4 others)` | Triggered when: Users count (+1 other variants (and other variations) |
| `No Client Service Coodinator assigned to region` | `Exception` | `CompanyLearnersService.java -> completeNewCompanyLearners() (+1 others)` | Triggered when: User list size( |
| `No Client Services Coordinator assigned to region` | `Exception` | `NonSetaCompanyService.java -> createLearner()` | Triggered when: User list size( |
| `No Coodinator assigned to region` | `Exception` | `ActiveContractDetailService.java -> completeWorkflow()` | Triggered when: User list size( |
| `No DG allocation prepared, please enter levy number and do allocation` | `Exception` | `DgAllocationService.java -> requesteWorkflow()` | General system failure or invalid processing state |
| `No Entries To Process` | `Exception` | `DataFixService.java -> generateDgVerificationByWspIDScript()` | Triggered when: Wsp is provided |
| `No Learner Ratio Available` | `Exception` | `CompanyLearnersService.java -> checkIfCanAssignMentor()` | Triggered when: Learner mentor ratio get no of learners( |
| `No Learning programme` | `Exception` | `LearnerRegistrationFormUI.java -> completeQualificationByLearningProgrammeQual() (+3 others)` | Triggered when: L size( |
| `No Linked unit standard for this qualification` | `Exception` | `SummativeAssessmentReportService.java -> findByCompanylearners()` | Triggered when: Qualification unit standards list is missing |
| `No Linked unit standard for this wualification` | `Exception` | `SummativeAssessmentReportService.java -> findByCompanylearners()` | Triggered when: Learnership unit standards list is missing ) (+3 other variants (and other variations) |
| `No Moderator legacy data found for the ID number provided` | `Exception` | `AssesorModiratorUI.java -> callBackMethod()` | Triggered when: Form user get postal address( |
| `No Moderators Assigned To Test Centre. Please Assign Moderators Before Proceeding.` | `Exception` | `LegacyTradeTestRegistrationUI.java -> prepModeratorSelectionList() (+2 others)` | Triggered when: Company user selection list is empty( |
| `No Primary Designation found in the lookup table. Designation code to be added: PRI_SDP` | `Exception` | `TrainingProviderUI.java -> addPrimaySDP()` | Triggered when: Designation is missing |
| `No Primary SDF Assiged for the company` | `Exception` | `MgVerificationService.java -> submitMgVerificationToSdf()` | Triggered when: Evidance required |
| `No Primary SDFs found!` | `Exception` | `SendBulkMailUI.java -> addCompanyToList()` | Triggered when: Primary sdf is missing |
| `No Qualification XML file was provided to process ` | `Exception` | `LoadQualification.java -> loadAndProcessQualifications()` | Triggered when: File qualification is missing |
| `No Qualifications details found, please conatct support` | `Exception` | `AssesorModiratorUI.java -> loadAssessorLegacyQualification() (+1 others)` | Triggered when: Not at least one qual to be processed |
| `No Quality Assuror assigned to region` | `Exception` | `TrainingProviderVerficationService.java -> completeWorkflowToQualityAssuror()` | Triggered when: User list size( |
| `No Recognition Agreement in Place` | `CategorizationException` | `CategorisationService.java -> recognitionAgreementInPlace()` | Triggered when: Company get recognition agreement( |
| `No Region Client Service Administrator for the region` | `Exception` | `EmployeeCompanyLearnersService.java -> rejectCompanyLearners() (+4 others)` | Triggered when: Users is missing \|\| users size( |
| `No Sdf assigned to a company` | `Exception` | `DgAllocationService.java -> finalApproveParent() (+1 others)` | Triggered when: Dg allocation parent date appealed (+5 other variants (and other variations) |
| `No Training Comittee in Place` | `CategorizationException` | `CategorisationService.java -> trainingCommitteeChecks()` | Triggered when: Tc size( |
| `No Training Provider assigned for this application ` | `Exception` | `TrainingProviderVerficationService.java -> finalApproveModeration()` | Triggered when: User list size( |
| `No Training provider` | `Exception` | `TrainingProviderVerficationService.java -> completeWorkflowToQualityAssuror()` | Triggered when: Company is missing \|\| company id( |
| `No Unit Standards XML file was provided to process ` | `Exception` | `LoadQualification.java -> loadAndProcessUnitStandards()` | Triggered when: File unit standards is missing |
| `No UnitsStandard for qualification` | `Exception` | `CompanyLearnersService.java -> requestVerificationLearner() (+1 others)` | Triggered when: Unit standards is provided and unit standards size( |
| `No WSP data found` | `Exception` | `TestDownloadUI.java -> generateImpementationPlan()` | Triggered when: Wsp list is provided and wsp list size( |
| `No WSP information found` | `Exception` | `WspReportingUI.java -> prepareCompanyWSPList() (+1 others)` | Triggered when: Wsp list count (+1 other variants (and other variations) |
| `No agreements/contracts that have expired found` | `Exception` | `CompanyLearnersService.java -> checkExpiredContactsAndSendNotification() (+1 others)` | Triggered when: Expired list is missing \|\| expired list size( |
| `No assessor assigned` | `Exception` | `CompanyLearnersTradeTestLegacyUI.java -> completeWorkflowWithInitiatorOneWithoutRegion()` | Triggered when: Companylearnerstradetest get assessor application( |
| `No available mentor for this learner` | `Exception` | `CompanyLearnersService.java -> createLearner() (+2 others)` | Triggered when: Cl get site( |
| `No available mentor(s) for this workplace approval` | `Exception` | `CompanyLearnersService.java -> checkSmeQualificationMentor() (+2 others)` | Triggered when: Not valid |
| `No company assigned to a wsp` | `Exception` | `DgAllocationService.java -> finalApproveParent() (+1 others)` | Triggered when: Dg allocation parent date appealed (+5 other variants (and other variations) |
| `No data found` | `Exception` | `LegacyLearnershipService.java -> legacyLearnershipUpdate() (+2 others)` | Triggered when: List size( |
| `No employer contact person found.` | `Exception` | `CompanyLearnersService.java -> sendLPMFM007A() (+1 others)` | Triggered when: Employer contact person is missing \|\| employer contact person id( |
| `No gaurdian assigned to this application` | `Exception` | `CompanyLearnersOtpSignoffService.java -> completeCompanyLearnersOld()` | Triggered when: S is missing |
| `No learner assigned to this application` | `Exception` | `CompanyLearnersOtpSignoffService.java -> completeCompanyLearnersOld()` | Triggered when: S is missing |
| `No learner mentor ratio assigned to a qualification, please contact support` | `Exception` | `CompanyLearnersService.java -> checkSmeQualificationMentor() (+2 others)` | Triggered when: Qualification get learner mentor ratio( |
| `No learning programme avaliable for the qualification selected` | `Exception` | `QualificationDAO.java -> findLearningProgrammeByQual()` | Triggered when: Qual get learningprogrammequal( |
| `No moderator assigned` | `Exception` | `CompanyLearnersTradeTestLegacyUI.java -> completeWorkflowWithInitiatorOneWithoutRegion()` | Triggered when: Companylearnerstradetest get moderator application( |
| `No training provider assigned to this application` | `Exception` | `CompanyLearnersOtpSignoffService.java -> completeCompanyLearnersOld()` | Triggered when: S is missing |
| `No training provider has been accredited for the selected qualification` | `Exception` | `LearnerRegistrationFormOtpSignoffUI.java -> continueValidateSdpAccreditaion() (+1 others)` | General system failure or invalid processing state |
| `No unit standard(s) available` | `Exception` | `NonSetaQualificationsCompletionService.java -> requestNonSetaQualificationsCompletion()` | Triggered when: Unit standards size( |
| `No user found ` | `Exception` | `LegacyCompanyLearnersService.java -> rejectCompanyLearners()` | Triggered when: Users is missing \|\| users size( |
| `No wsp assigned to a DG Allocation` | `Exception` | `DgAllocationService.java -> finalApproveParent() (+1 others)` | Triggered when: Dg allocation parent date appealed (+5 other variants (and other variations) |
| `Not a valid search` | `Exception` | `ResendConfirmEmailUI.java -> callBackMethod()` | Triggered when: Object instanceof users |
| `Not enough levy data loaded to do calculation` | `CategorizationException` | `CategorisationService.java -> paidLast12MonthsLevy()` | Triggered when: L size( |
| `Null company details` | `Exception` | `InterSetaTransferUI.java -> getPassedCompanyDetailsSearch()` | Triggered when: Company is missing |
| `Null user details` | `Exception` | `InterSetaTransferService.java -> sessionUserSearch()` | Triggered when: Org apache commons lang3 string utils is not blank(active user get passport number( |
| `Number of Learners Completed cannot be greater than Number of Learners Started` | `Exception` | `MgVerificationDetailsService.java -> checkValues() (+1 others)` | Triggered when: Mg get no learners completed( |
| `Number of Learners Completed cannot be less than zero` | `Exception` | `MgVerificationDetailsService.java -> checkValues() (+1 others)` | Triggered when: Mg get no learners completed( |
| `Number of Learners Completed with Number of Learners Withdrawn  cannot be greater than Number of Learners Started` | `Exception` | `MgVerificationDetailsService.java -> checkValues() (+1 others)` | Triggered when: Mg get no learners completed( |
| `Number of Learners Planned cannot be less than zero` | `Exception` | `MgVerificationDetailsService.java -> checkValues() (+1 others)` | Triggered when: Mg get no planned learners( |
| `Number of Learners Started cannot be less than zero` | `Exception` | `MgVerificationDetailsService.java -> checkValues() (+1 others)` | Triggered when: Mg get no learners started( |
| `Number of Learners Withdrawn cannot be greater than Number of Learners Started` | `Exception` | `MgVerificationDetailsService.java -> checkValues() (+1 others)` | Triggered when: Mg get no learners withdrawn( |
| `Number of Learners Withdrawn cannot be less than zero` | `Exception` | `MgVerificationDetailsService.java -> checkValues() (+1 others)` | Triggered when: Mg get no learners withdrawn( |
| `Number of learners have exceeded mentor ratio` | `Exception` | `CompanyLearnersService.java -> checkSmeQualificationMentor() (+1 others)` | Triggered when: Not valid |
| `Number of learners have exceeded mentor ration` | `Exception` | `EmployeeCompanyLearnersService.java -> checkSmeQualificationMentor()` | Triggered when: Not valid |
| `Number of learners have exceeded the learner-mentor ratio` | `Exception` | `LearnerRegistrationFormUI.java -> startLearnerReg() (+1 others)` | Triggered when: Not company learners service check sme qualification mentor(qualification, company |
| `Number of males and females gender cannot exceed total number of learners` | `Exception` | `DiscretionaryGrantDGUI.java -> generateEmployeesEmployed()` | Triggered when: Total > mandatory grant get amount( |
| `Ofo code is invalid` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Ofo codes service finding by code ei get ofo code( |
| `Ofo or Specialisation code is required.` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: (ei get specialisation code( |
| `Only accredited Assessor or Moderator can be added` | `Exception` | `TrainingProviderApplicationService.java -> avialabilityOfFacilitorAssModValidation()` | Triggered when: Am application list is missing \|\| am application list size( |
| `Only an employee of the company can be registered as a Mentor` | `Exception` | `SitesSmeUI.java -> searchForUser()` | Triggered when: Idpassport == id passport enum rsa id |
| `Only employee can be registered as a mentor` | `Exception` | `SitesSmeUI.java -> searchForUser()` | Triggered when: Idpassport == id passport enum rsa id |
| `Organisation type error for the employer` | `Exception` | `CompanyLearnersService.java -> createNewLearnerBySDP() (+1 others)` | Triggered when: Cl get employer( |
| `Password cannot be your Firstname or Surname.` | `Exception` | `GenericUtility.java -> checkPassword()` | Triggered when: New password matches u get last name( |
| `Password must be 8 characters long.` | `Exception` | `GenericUtility.java -> checkPassword()` | Triggered when: New password length( |
| `Plaese signoff before proceeding` | `Exception` | `LearnerReviewUI.java -> finalApproveWorkflow()` | Triggered when: Signoff is false |
| `Please Assign A User For Sign Off` | `Exception` | `SignoffService.java -> validateSignoffUsers()` | Triggered when: User required |
| `Please Provide Assessment Tasks Information` | `Exception` | `CompanyLearnersTradeTestService.java -> completeWorkflowWithTestCenterTradeTest()` | Triggered when: Trade test task result service count of all trade test task result by trade test id company learners trade test |
| `Please Provide Evaluator Outcome / Comment For Missing Entries` | `Exception` | `TpDetailUI.java -> completeTask()` | Triggered when: Not show complete |
| `Please Select Collection Date` | `Exception` | `GeneratedCertificateUI.java -> completeWorkflowFinal()` | Triggered when: Trainingproviderverfication get collection date( |
| `Please Select One Sign Off Before Proceeding` | `Exception` | `WorkPlaceApprovalUI.java -> signOffTask()` | Triggered when: Signoff user (+1 other variants (and other variations) |
| `Please Select Only One Sign Off Before Proceeding` | `Exception` | `WorkPlaceApprovalUI.java -> signOffTask() (+1 others)` | Triggered when: Signoff user (+2 other variants (and other variations) |
| `Please Select Qualification` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfQualAdded() (+2 others)` | Triggered when: Qualification matches qc get qualification( |
| `Please Select Review Date And Click The Set Review Date Button Before Proceeding.` | `Exception` | `LearnerReviewHoldingRoomUI.java -> completeWorkflow() (+2 others)` | Triggered when: Scheduled event get from date time( |
| `Please Select Skills Programme` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfSkillsProgAdded() (+1 others)` | Triggered when: Tp sp get skills program( |
| `Please Select Skills Set` | `Exception` | `SDPExtensionOfScopeUI.java -> addSkillsSetToList() (+2 others)` | Triggered when: Skills set is missing |
| `Please Select Unit Standard` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfUsAdded() (+2 others)` | Triggered when: Unit standard is missing |
| `Please Sign Off Before Proceeding` | `Exception` | `WorkPlaceApprovalUI.java -> signOffTask() (+2 others)` | Triggered when: Signoff user (+2 other variants (and other variations) |
| `Please Sign Off Before Proceeding.` | `Exception` | `SignoffService.java -> validateSignoff()` | Triggered when: Sign off required |
| `Please Sign Off Before Submission` | `Exception` | `ActiveContractsUI.java -> completeSignOffTask()` | Triggered when: Projectimplementationplan list is provided and not projectimplementationplan list is empty( |
| `Please Specify which entity is requesting this transfer` | `Exception` | `LearnersUI.java -> checkRequiredFields() (+1 others)` | Triggered when: Company learners transfer get learner transfer type( |
| `Please Upload Required Document` | `Exception` | `WorkPlaceApprovalUI.java -> witdrawWorkPlaceApproval() (+3 others)` | Triggered when: Doc is missing ) (+1 other variants (and other variations) |
| `Please accept Code Of Conduct` | `Exception` | `SDPExtensionOfScopeUI.java -> requestNonMerSETAExtensionOfScope() (+2 others)` | Triggered when: Select training provider application code of conduct accepted (+1 other variants (and other variations) |
| `Please accept code of conduct` | `Exception` | `AssesorModiratorUI.java -> sendReRegistrationRequest() (+1 others)` | Triggered when: Code of conduct accepted is missing \|\|code of conduct accepted==false) (+3 other variants (and other variations) |
| `Please accept the sign off before proceeding.` | `Exception` | `WorkplaceMonitoringSiteVisitService.java -> signOffWorkplaceMonitoring()` | Triggered when: Signoff accept (+1 other variants (and other variations) |
| `Please add Assessor/Moderator Registration Approval to the agenda of the selected Review Committee meeting` | `Exception` | `AssessorModeratorApplicationService.java -> assignMeetingAgender()` | Triggered when: Meeting agender is provided |
| `Please add Learner Termination Application to the agenda of the selected Review Committee meeting` | `Exception` | `CompanyLearnersTerminationService.java -> assignMeetingAgender()` | Triggered when: Meeting agender is provided |
| `Please add Training Provider Application Approval to the agenda of the selected Review Committee meeting` | `Exception` | `SDPExtensionOfScopeService.java -> assignMeetingAgender()` | Triggered when: Meeting agender is provided |
| `Please add TrainingProviderApplication Approval to the agenda of the selected Review Committee meeting` | `Exception` | `CompanyService.java -> updateReviewCommitteeDate() (+1 others)` | Triggered when: Meeting agender is provided ) (+1 other variants (and other variations) |
| `Please add a comment` | `Exception` | `VerificationLearnersHoldingUI.java -> saveAuditorMonitorReview()` | Triggered when: Auditor monitor review get evidence required( |
| `Please add a moderator` | `Exception` | `VerificationLearnersHoldingUI.java -> saveUnitStandards() (+2 others)` | Triggered when: Summative assessment report unit standards get moderator application( |
| `Please add aleast 1 language` | `Exception` | `AssesorModiratorUI.java -> doneUserBit()` | Triggered when: Users language list size( |
| `Please add an assessor` | `Exception` | `VerificationLearnersHoldingUI.java -> saveUnitStandards() (+2 others)` | Triggered when: Summative assessment report unit standards get assessor application( |
| `Please add at least one user` | `Exception` | `ReviewCommitteeMeetingUI.java -> addEvent()` | Triggered when: Meeting users list size( |
| `Please add atleast on learner to generate certificate` | `Exception` | `GenerateCertificatesUI.java -> generateAllCertificate() (+1 others)` | Triggered when: Trainingproviderverfication list size( |
| `Please add atleast one certificate to be dowloaded` | `Exception` | `GeneratedCertificateUI.java -> generateAndZipDocuments()` | Triggered when: Trainingproviderverfication list size( |
| `Please add atleast one disability` | `Exception` | `LearnerRegistrationFormUI.java -> startLearnerReg() (+5 others)` | Triggered when: Users disability list size( |
| `Please add atleast one language` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> startLearnerReg() (+5 others)` | Triggered when: Not homelang |
| `Please add contact person` | `Exception` | `TrainingProviderApplicationUI.java -> addAssessorModValidation() (+1 others)` | Triggered when: Not contact person list contains(contact person) (+3 other variants (and other variations) |
| `Please approval documentation` | `Exception` | `DGYearService.java -> generateDGYear()` | Triggered when: Dg year get docs( |
| `Please approve at least one qualificaiton, unit standard or skills programme` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfQualificationIsApprove() (+2 others)` | Triggered when: Not approve |
| `Please approve at least one qualificaiton, unit standard, Learnership or skills programme` | `Exception` | `AmDetailUI.java -> checkIfQualificationIsApprove()` | Triggered when: Not check if legacy qualification is approve (+1 other variants (and other variations) |
| `Please assign a different designation before proceeding. Selected current designation.` | `Exception` | `TrainingProviderApplicationUI.java -> submitUpdateDesignationSdp()` | Triggered when: New action get current designation( |
| `Please assign atleast one qualification before proceeding.` | `Exception` | `TrainingProviderApplicationAssessorModLinkUI.java -> addNewQualificationScope()` | Triggered when: Qualification list is empty( |
| `Please assign new designation before proceeding.` | `Exception` | `TrainingProviderApplicationUI.java -> submitUpdateDesignationSdp()` | Triggered when: New action get new designation( |
| `Please close all mitigation plans before proceeding with final approval.` | `Exception` | `WorkplaceMonitoringSiteVisitService.java -> validiationBeforeFinalApproval()` | Triggered when: Workplace monitoring mitigation plan service count of open mitigation plan by site visit id entity id( |
| `Please complete moderation report` | `Exception` | `VerificationLearnersHoldingUI.java -> createAdministratorTask()` | Triggered when: Scheduled event get moderation enum( |
| `Please complete self evaluation details by specifying if evidence is required or not` | `Exception` | `SDPExtensionOfScopeUI.java -> validateSelfEvaluationLazyLoad() (+3 others)` | Triggered when: Auditor monitor review service count of by target class key where evidance avalaible not porvided with relevent sdp re accreditation class (+4 other variants (and other variations) |
| `Please complete site visit details` | `Exception` | `SDPExtensionOfScopeUI.java -> validateSiteVisit() (+2 others)` | Triggered when: Tp application site visit date (+1 other variants (and other variations) |
| `Please ensure all documents are uploaded` | `Exception` | `ExtensionRequestService.java -> adminCreate() (+16 others)` | Triggered when: Doc id (+2 other variants (and other variations) |
| `Please ensure all documents are uploaded for the user` | `Exception` | `QdfCompanyService.java -> uploadDocuments()` | Triggered when: Task get process role( |
| `Please ensure all users assigned to sign off` | `Exception` | `WorkplaceMonitoringSiteVisitService.java -> validateSignOffusersAssigned()` | Triggered when: Not all users assigned |
| `Please ensure that a minimum of two contact person details are captured` | `Exception` | `TpDetailUI.java -> completeTask_OLD()` | Triggered when: Company user list size( |
| `Please ensure the total number of employees is captured for the company` | `Exception` | `EmployeesImportUI.java -> handleFileUpload()` | Triggered when: Company get number of employees( |
| `Please ensure you select 3 options for each occupational category in your organisation for each section` | `Exception` | `WspSkillsGapUI.java -> saveSectionsResults()` | Triggered when: Calulate can edit(a |
| `Please enter ID/Accreditaion number` | `Exception` | `EmployeeLearnersVerificationUI.java -> findUserByIDOrPassport()` | Triggered when: Idnumber is provided and not idnumber matches "" |
| `Please enter select qualification details` | `ValidationException` | `CompanyService.java -> createTPAndCompanyAndSendTaskVersionTwo()` | Triggered when: Data entities size( |
| `Please legacy data code` | `Exception` | `LegacyLearnershipUI.java -> legacyLearnershipUpdate()` | Triggered when: Legacy code matches "" |
| `Please make sure all assessment information is available` | `Exception` | `VerificationLearnersUI.java -> addToList()` | Triggered when: Not service check assessments(trainingproviderverfication |
| `Please make sure all assessment information is loaded` | `Exception` | `EmployeeLearnersVerificationUI.java -> requestExternalModeration()` | Triggered when: Service check fieldsprovided(trainingproviderverfication |
| `Please make sure all learner moderation has been completed` | `Exception` | `VerificationLearnersHoldingUI.java -> createAdministratorTask()` | Triggered when: Not check |
| `Please make sure company moderation is completed` | `Exception` | `VerificationLearnersHoldingUI.java -> createAdministratorTask()` | Triggered when: Not checkfields |
| `Please note that you are not accredited for this the selected programme. You may apply for accreditation or transfer the learner to an accredited provider.` | `Exception` | `LearnersUI.java -> requestChange()` | Triggered when: Not accredited |
| `Please provide Date Of Investigation` | `Exception` | `CompanyLearnersTerminationService.java -> sendOnesidedTerminationTask()` | Triggered when: Compnay learner termination get investigate date( |
| `Please provide SDL number` | `Exception` | `LegacyUnitStandardUI.java -> legacyAccreditationUpdate() (+2 others)` | Triggered when: Levy number matches "" |
| `Please provide accreditation number` | `Exception` | `LegacyUnitStandardUI.java -> legacyAccreditationUpdate() (+2 others)` | Triggered when: Accreditation number matches "" |
| `Please provide at least 2 contact persons` | `Exception` | `TrainingProviderUI.java -> doneAddingContactPerson()` | Triggered when: Contact person list size( |
| `Please provide at least 2 secondary contact persons (exclduing Primary SDP designation)` | `Exception` | `TrainingProviderUI.java -> doneAddingContactPersonV2()` | Triggered when: Users assigned < min users allowed |
| `Please provide at least one qualification or Unit` | `Exception` | `AssessorModeratorApplicationService.java -> requestExternsionOfScope()` | Triggered when: Am get application type( |
| `Please provide emploer's contact details` | `Exception` | `CompanyLearnersTradeTestService.java -> downloadLPMFM008()` | Triggered when: Employer contact person is missing |
| `Please provide emploer's details` | `Exception` | `CompanyLearnersTradeTestService.java -> downloadLPMFM008()` | Triggered when: Company learners trade test get employer( |
| `Please provide intervention type` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> startLearnerReg() (+6 others)` | Triggered when: Companylearners get intervention type( |
| `Please provide new SDP contact user before proceeding.` | `Exception` | `TrainingProviderApplicationUI.java -> submitNewSdpContactPerson()` | Triggered when: New user assigned is missing |
| `Please provide new code` | `Exception` | `LegacyLearnershipUI.java -> legacyLearnershipUpdate()` | Triggered when: New code matches "" |
| `Please provide non credid bearing description` | `Exception` | `CompanyLearnersService.java -> validateLearnerChange()` | Triggered when: Company learners change get non credid bearing description( |
| `Please provide on self evaluation: Evaluator Outcome: Evidence Available & Comments where missing` | `Exception` | `SDPExtensionOfScopeUI.java -> validateSelfEvaluationCommentsLazyLoad() (+1 others)` | Triggered when: Auditor monitor review service count of by target class key where outcome comment not provided training provider application class (+1 other variants (and other variations) |
| `Please provide percentage` | `Exception` | `TrainingProviderModerationUI.java -> createLearnersForModerate()` | Triggered when: Scheduled event get percentage enum( |
| `Please provide qualification details` | `ValidationException` | `SDPExtensionOfScopeUI.java -> doneQualificationDetails() (+3 others)` | Triggered when: Company qualifications count (+1 other variants (and other variations) |
| `Please provide required information` | `Exception` | `MgVerificationDetailsService.java -> checkValues() (+1 others)` | Triggered when: Mg get no learners started( |
| `Please provide required information before proceeding` | `Exception` | `MandatoryGrantVerificationApplicationUI.java -> submitMGVerification()` | Triggered when: Count is greater than zero |
| `Please provide self evaluation comments` | `Exception` | `SDPExtensionOfScopeUI.java -> validateSelfEvaluationComments() (+3 others)` | Triggered when: Audit get comment( |
| `Please provide self evaluation: Evaluator Outcome: Evidence Available & Comments where missing` | `Exception` | `SDPReAccreditationUI.java -> validateSelfEvaluationCommentsLazyLoaded()` | Triggered when: Auditor monitor review service count of by target class key where outcome comment not provided sdp re accreditation get class( |
| `Please provide tarining provider` | `Exception` | `LearnersUI.java -> requestTransfer()` | Triggered when: Company learners transfer get transfer training provider application( |
| `Please provide transfer reason` | `Exception` | `LearnersUI.java -> checkRequiredFields() (+1 others)` | Triggered when: Company learners transfer get transfer reason( |
| `Please provide user required for signoff` | `Exception` | `MandatoryGrantVerificationApplicationUI.java -> submitMGVerification()` | Triggered when: Signoff missing |
| `Please provide your training company` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> startLearnerReg() (+6 others)` | Triggered when: Company is missing \|\| company id( |
| `Please qualification code` | `Exception` | `LegacyLearnershipService.java -> legacyLearnershipUpdate()` | Triggered when: Qualification code matches "" |
| `Please run the data process before populating missing post codes.` | `Exception` | `PostCodeLinkService.java -> populateMissingPostCodeInformation()` | Triggered when: Count where number not assigned( |
| `Please selct user to add` | `Exception` | `ReviewCommitteeMeetingUI.java -> addUser()` | Triggered when: Not meeting users list contains(meeting user |
| `Please select Review Date` | `Exception` | `QdfCompanyUI.java -> setVisitDateOnWorkplaceApproval()` | Triggered when: Selected qdf company get review date( |
| `Please select Skills Programme` | `Exception` | `TrainingProviderUI.java -> addSkillsProgramToList() (+1 others)` | Triggered when: Skills program is missing ) (+1 other variants (and other variations) |
| `Please select Skills Set` | `Exception` | `TrainingProviderUI.java -> addSkillsSetToList() (+1 others)` | Triggered when: Skills set is missing ) (+1 other variants (and other variations) |
| `Please select Transfer Request Type` | `Exception` | `LearnersUI.java -> checkRequiredFields() (+1 others)` | Triggered when: Company learners transfer get transfer request type( |
| `Please select Unit Standard` | `Exception` | `TrainingProviderUI.java -> addNonSetaUnitStandardToList() (+1 others)` | Triggered when: Unit standard is missing ) (+1 other variants (and other variations) |
| `Please select YES/NO` | `Exception` | `VerificationLearnersHoldingUI.java -> saveAuditorMonitorReview()` | Triggered when: Auditor monitor review get evidence required( |
| `Please select a reject reason` | `Exception` | `LearnerRegistrationFormUI.java -> finalRejectWorkflowMerseta() (+44 others)` | Triggered when: Get session ui (+3 other variants (and other variations) |
| `Please select at least one meeting agenda` | `Exception` | `ReviewCommitteeMeetingUI.java -> addEvent()` | Triggered when: Selected meeting agenda list size( |
| `Please select atleast one change reason` | `Exception` | `LearnersUI.java -> requestDocumentsChange()` | Triggered when: Date change reason selection list size( |
| `Please select company` | `Exception` | `LearnersUI.java -> checkRequiredFields() (+2 others)` | Triggered when: Company learners transfer transfer to company (+1 other variants (and other variations) |
| `Please select home language` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> startLearnerReg() (+5 others)` | Triggered when: Not home language selected) (+1 other variants (and other variations) |
| `Please select if you wish to generate certificate or add certificate details` | `Exception` | `EmployeesLearnersRegistrationUI.java -> requestVerificationLearner()` | Triggered when: Training provider verfication get generate add enum( |
| `Please select meeting members` | `Exception` | `QdfCompanyUI.java -> setVisitDateOnWorkplaceApproval()` | Triggered when: Selected users size( |
| `Please select qualification` | `Exception` | `TrainingProviderUI.java -> addNonSetaQualificationToList() (+1 others)` | Triggered when: Qualification is missing ) (+1 other variants (and other variations) |
| `Please select reject reason(s)` | `Exception` | `DgAllocationAppealUI.java -> finalRejectAppeal()` | Triggered when: Selected reject reason size( |
| `Please select rejection reason(s)` | `Exception` | `ActiveContractsUI.java -> finalRejectWorkflow() (+2 others)` | Triggered when: Selected reject reason size( |
| `Please select the moderation outcome` | `Exception` | `VerificationLearnersHoldingUI.java -> saveInformation()` | Triggered when: Scheduled event get moderation enum( |
| `Please select withrawal reasons` | `Exception` | `LearnersUI.java -> requestWithdrawal()` | Triggered when: Withdraw reject reason size( |
| `Please sign off before proceeding` | `Exception` | `MandatoryGrantVerificationUI.java -> uploadMGVerificationEvidence()` | Triggered when: Not signoff provided |
| `Please signoff before proceeding` | `Exception` | `LearnerRegistrationFormUI.java -> completeCompanyELearners() (+7 others)` | Triggered when: Not sign off accept (+1 other variants (and other variations) |
| `Please signoff before you proceed` | `Exception` | `TrainingProviderModerationUI.java -> completeWorkflowDataModel()` | Triggered when: Not signoff |
| `Please specify the month or yaer. Both search criteria can be use in combination` | `ValidationException` | `SkillsRegistrationReportingUI.java -> searchSkillsByYearOrMon() (+1 others)` | Triggered when: Search month is missing and search year is missing |
| `Please specify what you want to change` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get learner change type enum( |
| `Please upload  supporting document for Skills SetList(s)` | `Exception` | `TpDetailUI.java -> validateSupportingDocs() (+2 others)` | Triggered when: Skills set list is provided and skills set list count (+2 other variants (and other variations) |
| `Please upload all documents` | `Exception` | `CompanyInfoUI.java -> bankingdetailsInsert()` | Triggered when: Doc get data( |
| `Please upload required  evidence` | `Exception` | `TpDetailUI.java -> validateSelfEvaluation()` | Triggered when: Audit get evidence required evaluator outcome( |
| `Please upload required document ` | `Exception` | `EmployeeCompanyLearnersService.java -> createNewLearnerBySDP() (+7 others)` | Triggered when: Doc get data( |
| `Please upload required evidence where applicable.` | `Exception` | `SDPExtensionOfScopeUI.java -> validateSelfEvaluationLazyLoad() (+1 others)` | Triggered when: Auditor monitor review service count of by target class key where outcome is type and no doc provided sdpextensionofscope class (+1 other variants (and other variations) |
| `Please upload supporting document for Skills Programme(s) ` | `Exception` | `TpDetailUI.java -> validateSupportingDocs() (+2 others)` | Triggered when: Tp skills program list is provided and tp skills program list count (+2 other variants (and other variations) |
| `Please upload supporting document for Unit Standard(s) ` | `Exception` | `TpDetailUI.java -> validateSupportingDocs() (+2 others)` | Triggered when: Unit standards is provided and unit standards count (+2 other variants (and other variations) |
| `Please upload supporting document for qualification(s)` | `Exception` | `TpDetailUI.java -> validateSupportingDocs() (+2 others)` | Triggered when: Qualification list is provided and qualification list count (+2 other variants (and other variations) |
| `Please upload the required documents` | `Exception` | `TrainingProviderVerficationService.java -> createWorkflowToQualityAssuror()` | Triggered when: Scheduled event get docs( |
| `Please use a different training provider` | `Exception` | `LearnersUI.java -> requestTransfer()` | Triggered when: Company learners get training provider application( |
| `Posting to date must be after Posting from date.` | `Exception` | `ReconByDateUI.java -> validateDates()` | Triggered when: To date inv before(from date inv |
| `Pro-forma Letter for Full Accreditation is not available for this application` | `Exception` | `TrainingProviderApplicationService.java -> downloadETQTP017()` | Triggered when: Tp application get certificate number( |
| `Processing successful !` | `Exception` | `LoadQualification.java -> loadAndProcessUnitStandards()` | General system failure or invalid processing state |
| `Project implementation error on registration when counting learners` | `Exception` | `CompanyLearnersService.java -> createLearner() (+1 others)` | Triggered when: Count == -1 |
| `Project inplemantation error on registration` | `Exception` | `CompanyLearnersService.java -> createLearner() (+1 others)` | Triggered when: Count >= (cl get project implementation plan( |
| `Property alredy assigned. Please assign a different property.` | `Exception` | `ReportGenerationPropertiesService.java -> checkIfPropertyUsed()` | Triggered when: Count by report property(report generation properties report property (+1 other variants (and other variations) |
| `Provide A Reason For Rejection` | `Exception` | `DiscretionaryGrantVerificationUI.java -> updateDgVerificationWithSignOffRecommendedReject()` | Triggered when: Selected reject reason size( |
| `Provide A Status for all entries not assigned` | `Exception` | `DgActiveContractsBulkApprovalWorkflowUI.java -> completeBulkWorkflow()` | Triggered when: Countof entries with no status assigned not = 0 |
| `Provide A Status for entries not assigned one` | `Exception` | `DgActiveContractsBulkApprovalWorkflowUI.java -> prepCompleteWorkflow()` | Triggered when: Countof entries with no status assigned not = 0 |
| `Provide Admin Percentage Before Proceeding` | `Exception` | `SarsLevyDetailCalculationService.java -> validiateInformation()` | Triggered when: Sars levy detail calculation get admin percentage( |
| `Provide All Qualification Aligment Data Before Proceeding.` | `Exception` | `WspDGService.java -> saveStatusUpdate()` | Triggered when: Mandatory grant service count of by wsp qalification alignment not provided entity, wsp report enum employmentdata |
| `Provide All Required Documents Before Appealing WPA` | `Exception` | `WorkPlaceApprovalUI.java -> signOffTaskWithAppeal() (+1 others)` | Triggered when: Doc id( |
| `Provide At Least 1 Qualification Before Proceeding` | `Exception` | `SitesSmeUI.java -> createUpdateSmeEntry()` | Triggered when: Sitessme sme qualifications list (+1 other variants (and other variations) |
| `Provide At Least One Mentor` | `Exception` | `WorkPlaceApprovalMentorsUI.java -> completeRegistration()` | Triggered when: Work place approval sites count (+1 other variants (and other variations) |
| `Provide At Least One Rejection Reason Before Proceeding` | `ValidationException` | `WorkPlaceApprovalUI.java -> finalRejectRegistration() (+2 others)` | Triggered when: Selected rejection reasons size( |
| `Provide At Least One Sign-off` | `Exception` | `WorkPlaceApprovalUI.java -> cloRecommendationApprovalSelection() (+1 others)` | Triggered when: Workplaceapproval get sign offs( |
| `Provide Atleast 1 Reason Before Proceeding` | `Exception` | `SiteVisitReportUI.java -> finalRejectTask()` | Triggered when: Selected reject reasons size( |
| `Provide Atleast One Entry For: Details Of Experience` | `Exception` | `CompanyLearnersTradeTestService.java -> entryValidiation()` | Triggered when: Service count of by trade test company learners trade test |
| `Provide Atleast One Entry For: Details Of Training` | `Exception` | `CompanyLearnersTradeTestService.java -> entryValidiation()` | Triggered when: Service two count of by trade test company learners trade test |
| `Provide Atleast One Entry For: List Of 			// Qualified Artisans / Trainers /Subject Matter Experts (SME) Per 			// Site Before Proceeding` | `Exception` | `SiteVisitReportUI.java -> sitevisitreportInsert()` | Triggered when: Sitevisitreport get site visit report sm es( |
| `Provide Atleast One Reason For Rejection Before Submission.` | `Exception` | `InitiatewspDgUI.java -> rejectToSdfToAppeal() (+1 others)` | Triggered when: Selected reject reason is missing \|\| selected reject reason size( |
| `Provide Atleast One Rejection Reason Before Proceeding` | `Exception` | `DgActiveContractsBulkApprovalWorkflowUI.java -> rejectWorkflowWithWithMersetaOne() (+1 others)` | Triggered when: Reject reasons list count (+1 other variants (and other variations) |
| `Provide Atleast One Sign Off User Before Proceeding` | `Exception` | `SiteVisitReportUI.java -> sitevisitreportInsert()` | Triggered when: Sitevisitreport get sign offs( |
| `Provide Atleast one rejection reason before proceeding.` | `Exception` | `PaymentRequestUI.java -> finalRejectWorkflow()` | Triggered when: Selected rejection reasons is empty( |
| `Provide Discretionary Percentage Before Proceeding` | `Exception` | `SarsLevyDetailCalculationService.java -> validiateInformation()` | Triggered when: Sars levy detail calculation get discretionary percentage( |
| `Provide Dispute Reason Before Trying To Add A Dispute.` | `Exception` | `SiteVisitReportUI.java -> disputeInsert()` | Triggered when: Dispute get reson for dispute( |
| `Provide Entity ID Before Proceeding` | `Exception` | `GpTransactionsTestUI.java -> validiateLNumber()` | Triggered when: Levy number is missing \|\| levy number trim( |
| `Provide Final Response Before Proceeding` | `Exception` | `DiscretionaryGrantVerificationUI.java -> checkFinalReponseProvided()` | Triggered when: Dg verification get final response( |
| `Provide First Name Of Mentor` | `Exception` | `SitesSmeUI.java -> createUpdateSmeEntry()` | Triggered when: Sitessmeupdate first name (+1 other variants (and other variations) |
| `Provide Grant Appplication Appeal In Required Documents Section Before Proceeding With Appeal` | `Exception` | `WspService.java -> appealGrantApplication() (+1 others)` | Triggered when: Document id( |
| `Provide If Learner is Competent or Not Yet Competent Before Proceeding` | `Exception` | `CompanyLearnersTradeTestService.java -> trainingProviderValidiation()` | Triggered when: Company learners trade test get competence enum( |
| `Provide Last Name Of Mentor` | `Exception` | `SitesSmeUI.java -> createUpdateSmeEntry()` | Triggered when: Sitessmeupdate last name (+1 other variants (and other variations) |
| `Provide Mandatory Percentage Before Proceeding` | `Exception` | `SarsLevyDetailCalculationService.java -> validiateInformation()` | Triggered when: Sars levy detail calculation get mandatory percentage( |
| `Provide New Date For Next Audit` | `Exception` | `TrainingProviderMonitoringWorkflowUI.java -> trainingprovidermonitoringCancel() (+2 others)` | Triggered when: New date is missing ) (+1 other variants (and other variations) |
| `Provide New Date For Next Monitoring` | `Exception` | `TrainingProviderMonitoringViewUI.java -> trainingprovidermonitoringCancel() (+2 others)` | Triggered when: New date is missing ) (+1 other variants (and other variations) |
| `Provide New Extension Date Before Proceeding` | `Exception` | `ActiveContractExtensionRequestWorkflowUI.java -> updateExtensionRequestDate()` | Triggered when: Not use system generated date and new extension date is missing |
| `Provide QCTO Percentage Before Proceeding` | `Exception` | `SarsLevyDetailCalculationService.java -> validiateInformation()` | Triggered when: Sars levy detail calculation get qcto percentage( |
| `Provide Rejection Reasons Before Proceeding` | `Exception` | `AdminReopenWspUI.java -> sendNotification() (+1 others)` | Triggered when: Selected rejection reasons is missing \|\| selected rejection reasons size( |
| `Provide Source Funding` | `Exception` | `CompanyLearnersService.java -> createLearner() (+1 others)` | Triggered when: Cl get source funding( |
| `Provide Test Dates Before Proceeding` | `Exception` | `CompanyLearnersTradeTestService.java -> trainingProviderValidiation()` | Triggered when: Not company learners trade test get test dates provided( |
| `Provide Whether: Training and Assessment or Assessment 					// Only` | `Exception` | `TrainingProviderUI.java -> checkFeildsProvided()` | Triggered when: (training provider application get training assessment( |
| `Provide Whether: Training and Assessment or Assessment Only` | `Exception` | `TrainingProviderUI.java -> doneLegacyCompanyBit()` | Triggered when: (training provider application get training assessment( |
| `Provide Year Before Proceeding` | `Exception` | `SarsLevySchemeYearReturnsService.java -> validiateInformation() (+1 others)` | Triggered when: Sars levy scheme year returns for scheme year (+1 other variants (and other variations) |
| `Provide a reason for rejection.` | `Exception` | `DiscretionaryGrantVerificationUI.java -> checkOnRecommendations()` | Triggered when: Mandatory grant recommendation get reject reasons( |
| `Provide a type before proceeding` | `Exception` | `TrainingProviderApplicationUI.java -> createNewAssessorModeratorLink()` | Triggered when: Assessor mod type is missing |
| `Provide all answers before proceeding.` | `Exception` | `WorkplaceMonitoringLearnerSurveyService.java -> createFullSurvey()` | Triggered when: Answer get answer( |
| `Provide all answers for the survey before proceeding.` | `Exception` | `WorkplaceMonitoringLearnerSurveyService.java -> validiateInformation()` | Triggered when: Question get answer( |
| `Provide all documents stipulated by evidance required before proceeding.` | `Exception` | `MgVerificationDetailsService.java -> validateUploadEvidance()` | Triggered when: Docs outstanding |
| `Provide an Assessor Before Proceeding` | `Exception` | `CompanyLearnersTradeTestService.java -> trainingProviderValidiation()` | Triggered when: Company learners trade test get assessor application( |
| `Provide at least one reason for rejection before submission.` | `Exception` | `InitiatewspDgUI.java -> sendToManagerReject()` | Triggered when: Selected reject reason is missing \|\| selected reject reason size( |
| `Provide atleast one reason before proceeding` | `Exception` | `WorkplaceMonitoringSiteVisitNonComplianceWorkflowUI.java -> rejectBackToClo() (+1 others)` | Triggered when: Selected reject reasons list is missing \|\| selected reject reasons list is empty( |
| `Provide atleast one supporting document before proceeding` | `Exception` | `PrimarySdpRelinkUI.java -> submitSdpRemoval() (+1 others)` | Triggered when: Doc list is empty( |
| `Provide change reason` | `Exception` | `ProfileUI.java -> createTaskToUpdateProfile()` | Triggered when: Change reason get description( |
| `Provide email before sending` | `Exception` | `AdminReopenWspUI.java -> sendNotification() (+1 others)` | Triggered when: Email is missing \|\| email is empty( |
| `Provide email contents` | `Exception` | `SendBulkMailUI.java -> validationCheck()` | Triggered when: General message is missing \|\| general message is empty( |
| `Provide intervention type for configured questions` | `Exception` | `WorkplaceMonitoringLearnerSurveyAnswersUI.java -> copyInterventionTypeQuestions()` | Triggered when: Selected intervention type answers is missing ) (+1 other variants (and other variations) |
| `Provide intervention type for questions delete` | `Exception` | `WorkplaceMonitoringLearnerSurveyAnswersUI.java -> deleteQuestionsByInterventionType()` | Triggered when: Selected intervention type answers is missing |
| `Provide: SARS DHET Scheme Year For Filter or de-select scheme year filter.` | `Exception` | `MandatoryGrantsTransactionGpUI.java -> generateResultsByDate()` | Triggered when: Scheme year entered is missing |
| `Provider Type code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get provider type code( |
| `Provider cerror on registration` | `Exception` | `EmployeeCompanyLearnersService.java -> createLearner() (+1 others)` | Triggered when: Training provider is provided and training provider id (+1 other variants (and other variations) |
| `Provider error on registration` | `Exception` | `CompanyLearnersService.java -> createLearner() (+3 others)` | Triggered when: Training provider is provided and training provider id (+1 other variants (and other variations) |
| `Qualification Already Linked To The Tool` | `Exception` | `QualificationToolListService.java -> create()` | Triggered when: Cout qualification(entity get qualification( |
| `Qualification Code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get qualification code( |
| `Qualification already assigned to application. Please select a different qualification.` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | Triggered when: Company qualifications service count of by application and qualification training provider application class get name( |
| `Qualification already assigned. Please select a different qualification.` | `Exception` | `QualificationToolKitService.java -> createWithCheck()` | Triggered when: Count by qualification id(entity qualification (+1 other variants (and other variations) |
| `Qualification already exist in the list` | `Exception` | `AmDetailUI.java -> addQualification()` | Triggered when: Us get qualification( |
| `Qualification already in list, please select a different qualification. ` | `Exception` | `TrainingProviderApplicationAssessorModLinkUI.java -> addQualificationToNewScopeList()` | Triggered when: Already in list |
| `Qualification already in the list` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfQualAdded() (+2 others)` | Triggered when: Qualification matches qc get qualification( |
| `Qualification already linked to application. Please select a different qualification. ` | `Exception` | `TrainingProviderApplicationAssessorModLinkUI.java -> addQualificationToNewScopeList()` | Triggered when: Assessor moderator application service check if application linked to qualification(assessor moderator application view, selected qualification |
| `Qualification already updated` | `Exception` | `MonitorLegacyAssessorModUI.java -> addUnitStandardsToList()` | Triggered when: User qualifications get qualification updated( |
| `Qualification not found` | `Exception` | `LegacyLearnersUI.java -> redirectLegacySectTwentyEight() (+3 others)` | Triggered when: Legacyapprenticeship qualification (+6 other variants (and other variations) |
| `Qualification not linked to unit standard` | `Exception` | `SummativeAssessmentReportService.java -> findByCompanylearners()` | Triggered when: Unit standards get qualification( |
| `RSA ID Number/Passport Number is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get wsp report( |
| `RSA/Passport error when registering learner, please contact the merSETA Office` | `Exception` | `LegacyLearnersUI.java -> redirectLegacySectTwentyEight() (+3 others)` | Triggered when: Legacybursary id two (+8 other variants (and other variations) |
| `Region CLO not found` | `Exception` | `WorkPlaceApprovalMentorsService.java -> completeRegistration()` | Triggered when: U is missing |
| `SAQA code is invalid` | `Exception` | `EmployeesImportService.java -> doPivotalNonPivotalChecks()` | Triggered when: Is blank: ei get saqa id( |
| `SARS/DHET to date must be after SARS/DHET from date.` | `Exception` | `ReconByDateUI.java -> validateDates()` | Triggered when: To date before(from date |
| `SDL number aready exist in merSETA database` | `Exception` | `TrainingProviderUI.java -> levyNumberRegnumberValidation()` | Triggered when: Comp is provided |
| `SDP contact user email address provided is already registered on the application. Provide a new email address or contact merSETA support.` | `Exception` | `TrainingProviderApplicationUI.java -> submitNewSdpContactPerson()` | Triggered when: Users service count of users by email new user assigned get email( |
| `SDP contact user information validiation error. Please review error message.` | `Exception` | `TrainingProviderApplicationUI.java -> submitNewSdpContactPerson()` | Triggered when: Not setmis validiation errors trim( |
| `SDP contact user is already assigned to the company. Please provide a different user.` | `Exception` | `PrimarySdpRelinkUI.java -> searchTrainingProviderApplication() (+1 others)` | Triggered when: User assigned to company is greater than zero |
| `SDP contact user is underway in an approval process for the company. Please provide a different user.` | `Exception` | `PrimarySdpRelinkUI.java -> searchTrainingProviderApplication() (+1 others)` | Triggered when: User in workflow for company is greater than zero |
| `SETMIS Code must be unique` | `Exception` | `LearnershipService.java -> create()` | Triggered when: Dao find unique setmis code(entity |
| `SETMIS Validiation Exception. Please attend to errors before proceeding.` | `Exception` | `LegacySiteApplicationSiteAllocationUI.java -> submitNewEntry()` | Triggered when: Not setmis validiation site is empty( |
| `SIC Code / Chamber not configured correctly` | `Exception` | `GPPrepTransactionsService.java -> createOnGp()` | Triggered when: Comp get sic code( |
| `Select A Fin Year` | `Exception` | `WspReportingUI.java -> generateStatusReport()` | Triggered when: Selected year is missing |
| `Select A Site Before Proceeding` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> createLegacyWorkPlaceApproval() (+7 others)` | Triggered when: Selected site is missing |
| `Select A Unit Standard before Proceeding` | `Exception` | `QualificationUI.java -> addUnitStandard() (+1 others)` | Triggered when: Qualification unit standards unit standards (+1 other variants (and other variations) |
| `Select A Unit learnership before Proceeding` | `Exception` | `LearnershipUI.java -> prepUnitStandardAdd()` | Triggered when: Learnership link is missing |
| `Select A Year` | `Exception` | `WspReportingUI.java -> populateDgAllocationDetailedReportByFinYear() (+3 others)` | Triggered when: Selected year is missing |
| `Select A Year Before Proceeding` | `Exception` | `WspReportingUI.java -> generateTasksReport()` | Triggered when: Selected year is missing |
| `Select Atleast One Company Before Proceeding` | `Exception` | `SendBulkMailUI.java -> validationCheck()` | Triggered when: Companies selected is missing \|\| companies selected size( |
| `Select Designation Before Proceeding` | `Exception` | `SdpTypeService.java -> validiateDesignationUsed()` | Triggered when: Entity get designation( |
| `Select Financial Year Before Proceeding` | `Exception` | `WspReportingUI.java -> populateWspByFinancialYearAndStatus() (+1 others)` | Triggered when: Selected year is missing |
| `Select Last Working Day Of Learner` | `Exception` | `LearnersUI.java -> requestTermination()` | Triggered when: Company learners termination get last working day of learner( |
| `Select Level Before Proceeding` | `Exception` | `CompanyLearnersService.java -> validiationOnTradeTestRequest()` | Triggered when: Company learners trade test get designated trade level( |
| `Select Mentor Learner Ration` | `Exception` | `AssignWorkplaceApprovalQualificationsUI.java -> setRequiredNotRequiredAndMentorRatio()` | Triggered when: Saqa qualification get learner mentor ratio( |
| `Select One Reason For Rejection Before Proceeding` | `Exception` | `LearnerRegistrationFormUI.java -> rejectlearnerTransfer() (+4 others)` | Triggered when: Selected reject reason size( |
| `Select SAQA Qualification` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get qualification( |
| `Select SAQA UnitStandard` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get unit standard( |
| `Select SAQA qualification` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get qualification( |
| `Select Skills Programme` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get company learners( |
| `Select Termination Type` | `Exception` | `LearnersUI.java -> requestTermination()` | Triggered when: Company learners termination get termination type enum( |
| `Select Unitstandrd beofre deleting entry` | `Exception` | `QualificationUI.java -> deleteLink() (+1 others)` | Triggered when: Qualification unit standards is missing \|\| qualification unit standards id (+1 other variants (and other variations) |
| `Select User Required For Sign Off of MOA` | `Exception` | `DgAllocationAdditionalUI.java -> requestHigherallocationVersionTwo() (+1 others)` | Triggered when: User selection for moa sign off is missing \|\| user selection for moa sign off id( |
| `Select a grant year before proceeding` | `Exception` | `ExtensionRequestUI.java -> applyFilter()` | Triggered when: Filter options == 2 and selected year is missing |
| `Select a language` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> languagePreCheck() (+12 others)` | Triggered when: Ul is provided and ul get language( |
| `Select a qualification before proceeding.` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | Triggered when: Qualification is missing |
| `Select a skills program before proceeding.` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | Triggered when: Skills program is missing |
| `Select a skills set before proceeding.` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | Triggered when: Skills set is missing |
| `Select a unit standard before proceeding.` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | Triggered when: Unit standard is missing |
| `Select a year` | `Exception` | `DgReportingUI.java -> generateStatusReport()` | Triggered when: Selected year is missing |
| `Select atleast one language` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> submitLearnerRegistration() (+6 others)` | Triggered when: Users language list size( |
| `Select commencment date` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get commencment date( |
| `Select completion date` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get completion date( |
| `Select home language` | `Exception` | `CompanyLearnersMonitorUI.java -> homeLanguageCheck()` | Triggered when: Count == 0 |
| `Select learnership` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get learnership( |
| `Select one home language` | `Exception` | `CompanyLearnersMonitorUI.java -> homeLanguageCheck()` | Triggered when: Count > 1 |
| `Select one rejection reason before proceeding` | `Exception` | `TrainingProviderMonitoringWorkflowUI.java -> rejectCompanyLearners() (+1 others)` | Triggered when: Selected reject reason is missing \|\| selected reject reason count (+1 other variants (and other variations) |
| `Select reason for lost time` | `Exception` | `LearnersUI.java -> requestLostTime()` | Triggered when: Company learners lost time get lost time reason( |
| `Select scheme years` | `Exception` | `SarsLevyReconUI.java -> runForSchemeYears()` | Triggered when: Sars recon scheme years is missing \|\| seta recon scheme years is missing |
| `Select the date lost time ended` | `Exception` | `LearnersUI.java -> requestLostTime()` | Triggered when: Company learners lost time get lost time end date( |
| `Select the date lost time started` | `Exception` | `LearnersUI.java -> requestLostTime()` | Triggered when: Company learners lost time get lost time start date( |
| `Selected User Has Not Completed First Time Log In, Please Select A Different User For Sign Off` | `Exception` | `ActiveContractsUI.java -> changeSignOffUser() (+3 others)` | Triggered when: User selection for moa sign off status (+1 other variants (and other variations) |
| `Selected User Has Not Confirmed Their Email Address, Please Select A Different User For Sign Off` | `Exception` | `DgAllocationAdditionalUI.java -> requestHigherallocationVersionTwo() (+2 others)` | Triggered when: User selection for moa sign off status (+1 other variants (and other variations) |
| `Selected User Is In-Active, Please Select A Different User For Sign Off` | `Exception` | `ActiveContractsUI.java -> changeSignOffUser() (+4 others)` | Triggered when: User selection for moa sign off status (+1 other variants (and other variations) |
| `Selected training provider is not accredited for the selected learnership` | `Exception` | `LearnerRegistrationFormUI.java -> continueValidateSdpAccreditaion() (+1 others)` | General system failure or invalid processing state |
| `Selected training provider is not accredited for the selected qualification` | `Exception` | `LearnerRegistrationFormUI.java -> continueValidateSdpAccreditaion() (+1 others)` | General system failure or invalid processing state |
| `Selected training provider is not accredited for the selected skill programme` | `Exception` | `LearnerRegistrationFormUI.java -> continueValidateSdpAccreditaion() (+1 others)` | General system failure or invalid processing state |
| `Selected training provider is not accredited for the selected skill set` | `Exception` | `LearnerRegistrationFormUI.java -> continueValidateSdpAccreditaion() (+1 others)` | General system failure or invalid processing state |
| `Selected training provider is not accredited for the selected unit standard` | `Exception` | `LearnerRegistrationFormUI.java -> continueValidateSdpAccreditaion() (+1 others)` | General system failure or invalid processing state |
| `Selected user has not completed first time log in. Please select a different user for sign off.` | `Exception` | `WorkplaceMonitoringSiteVisitWorkflowUI.java -> changeSignOffUser()` | Triggered when: User selection for moa sign off get status( |
| `Selected user has not confirmed their email address. Please select a different user for sign off.` | `Exception` | `WorkplaceMonitoringSiteVisitWorkflowUI.java -> changeSignOffUser()` | Triggered when: User selection for moa sign off get status( |
| `Session Already Active. Please log out of current session or fully close browser and attempt login again.` | `Exception` | `LogonUI.java -> validateIfSessionActive()` | Triggered when: Get session ui( |
| `Sign Off Before Proceeding` | `Exception` | `LegacyTradeTestRegistrationUI.java -> completeTradeTestActionWithSignoff() (+3 others)` | Triggered when: Accept is missing \|\| not accept |
| `Sign off is not an extenral user, functionlaity not required.` | `Exception` | `SignoffService.java -> testExternalUserEmailSent()` | Triggered when: Signoff get temp signoff( |
| `Signoff error` | `Exception` | `CompanyLearnersOtpSignoffService.java -> completeCompanyELearners() (+5 others)` | Triggered when: Sign off is missing ) (+1 other variants (and other variations) |
| `Site already allocation to legacy application. Please select a different application.` | `Exception` | `LegacySiteApplicationSiteAllocationUI.java -> selectProviderApplication()` | Triggered when: Selected training provider application get training site( |
| `Skills Program Code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get skills program code( |
| `Skills Program and Skills Set not found` | `Exception` | `LegacyLearnersProviderUI.java -> redirectLegacySkillsprogramme()` | Triggered when: Legacyskillsprogramme get skills program( |
| `Skills Program not the same` | `Exception` | `VerificationLearnersUI.java -> checkIfSameQualification()` | Triggered when: Trainingproviderverfication get company learners( |
| `Skills Program/Set not found` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> runInit()` | Triggered when: Legacyskillsprogramme get skills set( |
| `Skills Programme already in the list` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfSkillsProgAdded() (+2 others)` | Triggered when: Tp sp skills program (+1 other variants (and other variations) |
| `Skills Set Code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get skills set code( |
| `Skills Set not the same` | `Exception` | `VerificationLearnersUI.java -> checkIfSameQualification()` | Triggered when: Trainingproviderverfication get company learners( |
| `Skills Setalready in the list` | `Exception` | `SDPExtensionOfScopeUI.java -> addSkillsSetToList() (+2 others)` | Triggered when: Skills set matches tpss get skills set( |
| `Skills program already assigned to application. Please select a different skills program.` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | Triggered when: Training provider skills programme service count of by application and skills programme audit get training provider application( |
| `Skills set already assigned to application. Please select a different skills set.` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | Triggered when: Training provider skills set service count of by application and skills set audit get training provider application( |
| `SkillsProgram not found` | `Exception` | `LegacyLearnersUI.java -> runInit() (+3 others)` | Triggered when: Legacyskillsprogramme skills program (+2 other variants (and other variations) |
| `Someone from helpdesk will be in contact shortly to assist you.` | `ValidationErrorException` | `RegisterService.java -> validateResend()` | Triggered when: Db user get email( |
| `Specialisation code is invalid` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Ofo codes service finding by specialisation ei get specialisation code( |
| `Start date is invalid` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVData()` | Triggered when: A get start date( |
| `Start date is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataOnUpdate()` | Triggered when: A get start date( |
| `Statement Of Qualifications is not available for this application` | `Exception` | `TrainingProviderApplicationService.java -> resendStatmentOfQualification()` | Triggered when: Tp application get certificate number( |
| `System Error. One entry of assessor / moderator type must be provided. Contact support!` | `Exception` | `AssessorModeratorCompanySitesDAO.java -> countByUserTrainingSiteAssModTypeList()` | Triggered when: Assessor mod type list is missing \|\| assessor mod type list is empty( |
| `The application cannot be processed as there is currently an application under review` | `Exception` | `AssessorModeratorApplicationService.java -> validateAssessorModeratorApp()` | Triggered when: Am app get status( |
| `The company does not exist` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> callBackMethod() (+5 others)` | Triggered when: Company is missing \|\| company id (+1 other variants (and other variations) |
| `The company does not have organasation type. Please contact your administrator` | `Exception` | `EmployeeCompanyLearnersService.java -> createLearner() (+5 others)` | Triggered when: Company get organisation type( |
| `The company is not workplace approved for this qualification` | `Exception` | `EmployeeCompanyLearnersService.java -> createLearner()` | Triggered when: Work place approval is missing |
| `The company selected does not have workplace approval for the selected qualification` | `Exception` | `LearnerRegistrationFormUI.java -> continueLearnerRegistration() (+1 others)` | Triggered when: Work place approval is missing |
| `The employee unique identifier must be populated` | `Exception` | `EmployeesImportService.java -> save()` | Triggered when: Is blank: ei get emp unique id( |
| `The levy file does not exist on the system` | `Exception` | `SarsLeviesPaidService.java -> create()` | Triggered when: Entity get sars files( |
| `The list is empty` | `MraAnnotationException` | `TechFiniumAnnotation.java -> processBean()` | Triggered when: (( list |
| `The number of disabled breakdown is incorrect. Values restored to zero.` | `Exception` | `MandatoryGrantService.java -> calcEmployeesBreakDown()` | Triggered when: Zero totals on error |
| `The number of employees captured for the company is already at maximum` | `Exception` | `EmployeesUI.java -> employeesInsert() (+1 others)` | Triggered when: Current count is greater than zero and current count >= company number of employees (+2 other variants (and other variations) |
| `The number of employees in the CSV file is greater than the total number of company employees` | `Exception` | `EmployeesImportUI.java -> handleFileUpload()` | Triggered when: Company get number of employees( |
| `The number of employees in the CSV file is less than the total number of company employees` | `Exception` | `EmployeesImportUI.java -> handleFileUpload()` | Triggered when: Company get number of employees( |
| `The number of male/female breakdown is incorrect. Values restored to zero.` | `Exception` | `MandatoryGrantService.java -> calcEmployeesBreakDown()` | Triggered when: Zero totals on error |
| `The object is null` | `MraAnnotationException` | `TechFiniumAnnotation.java -> processBean()` | Triggered when: Obj is missing |
| `The select user is not an accredited  Assessor/Moderator` | `Exception` | `TrainingProviderApplicationService.java -> avialabilityOfFacilitorAssModValidation()` | Triggered when: Am application list is missing \|\| am application list size( |
| `The select user is not an accredited assessor/moderator` | `Exception` | `TrainingProviderApplicationUI.java -> callBackMethod()` | Triggered when: New user assigned is provided and new user assigned id (+1 other variants (and other variations) |
| `The selected company is in use` | `Exception` | `SearchCompanyUI.java -> findBySDLTrainingProviderRegistartion()` | Triggered when: Tpa is provided and tpa size( |
| `The selected user is already assigned to the company` | `Exception` | `SDFCompanyHistoryService.java -> createSecondarySDF() (+1 others)` | Triggered when: Find by company and user(company, sdf |
| `The selected user is not an assessor 					// or a moderator` | `Exception` | `CompanyUsersUI.java -> getAssessorModTypeDD()` | Triggered when: L size( |
| `The selected user is not an assessor or 				// a moderator` | `Exception` | `CompanyUsersUI.java -> getAssessorModTypeDD()` | General system failure or invalid processing state |
| `The total number of Unit Standards connot be more that 120, please remove some of Unit Standards` | `Exception` | `SkillsRegistrationUI.java -> chechCredits()` | Triggered when: Credits>120 |
| `The training provider does not exist` | `Exception` | `LearnerRegistrationFormOtpSignoffUI.java -> callBackMethod() (+1 others)` | Triggered when: Training provider is missing \|\| training provider id( |
| `The uniaue 			// identifier must be populated` | `Exception` | `EmployeesImportService.java -> save()` | Triggered when: Is blank: ei get unique id( |
| `The unit standarts are not linked to the qualification!!! Please contact your administrator` | `Exception` | `CompanyLearnersService.java -> requestVerificationLearner() (+1 others)` | Triggered when: Summative assessment report unit standards is missing ) (+1 other variants (and other variations) |
| `There is a problem loading the CSV file. Please ensure all data is correct before trying again.` | `Exception` | `MandatoryGrantDetailService.java -> saveVersionTwo()` | Triggered when: A id number (+1 other variants (and other variations) |
| `There is an Assessor/Moderator application in process with the same qualification, please select a different qualification` | `Exception` | `AssessorModeratorApplicationService.java -> validateQualificationAccreditatation()` | Triggered when: Assessor moderator application list get(0 |
| `There is an application in process with the same Unit Standard, please select a different Unit Standard` | `Exception` | `AssessorModeratorApplicationService.java -> validateUniStandardAccreditatation()` | Triggered when: Assessor moderator application list get(0 |
| `There is an error with the learner application!!! Please contact your administrator` | `Exception` | `LearnersUI.java -> requestVerificationLearner() (+2 others)` | Triggered when: Company learners is missing ) (+1 other variants (and other variations) |
| `There is an error with the selected learner application!!! Please contact your administrator` | `Exception` | `LearnersUI.java -> requestVerificationLearnerByEmployee()` | Triggered when: Training provider monitoring get training provider( |
| `There is currently an open extension of scope. Please wait for it to be completed before submitting a re-submission.` | `Exception` | `SDPReAccreditationUI.java -> prepareResubmissionData()` | Triggered when: Open extensions is greater than zero |
| `There is currently an open extension of scope. Please wait for it to be completed before submitting another extension of scope.` | `Exception` | `SDPExtensionOfScopeUI.java -> prepareExtensionOfScope()` | Triggered when: Open extensions is greater than zero |
| `There is currently an open re-submission. Please wait for it to be completed before submitting an extension of scope.` | `Exception` | `SDPExtensionOfScopeUI.java -> prepareExtensionOfScope()` | Triggered when: Open resubmissions is greater than zero |
| `There is currently an open re-submission. Please wait for it to be completed before submitting another re-submission.` | `Exception` | `SDPReAccreditationUI.java -> requestReAccreditation()` | Triggered when: Open resubmissions is greater than zero |
| `There is historical accreditation details linked to this ID/Passport number. Please use the Legacy Assessor/Moderator Registration option to complete the system registration process` | `Exception` | `AssesorModiratorUI.java -> legacyAssessorRegValidatio()` | Triggered when: Assesor legacy list is provided and assesor legacy list count (+1 other variants (and other variations) |
| `There is no form number for this intervention` | `Exception` | `LearnerRegistrationFormUI.java -> continueRegistration() (+4 others)` | Triggered when: This intervention type is missing \|\| this intervention type get form( |
| `There is no meeting scheduled for this application` | `Exception` | `TrainingProviderModerationUI.java -> storeNewFile()` | Triggered when: Scheduled event is missing \|\| scheduled event id( |
| `This business process has already been configured` | `Exception` | `ConfigDocService.java -> checkIfRootAlreadyExist()` | Triggered when: Tmp is provided |
| `This learner has already been linked to this company` | `Exception` | `CompanyLearnerUsersService.java -> createNewLearner()` | Triggered when: Count company learner users(entity,company learner users get company( |
| `This option is available for first time applicants` | `Exception` | `AssessorModeratorApplicationService.java -> validateAssessorModeratorApp()` | Triggered when: Am app list is provided and am app list size( |
| `This option is available if a learner is absent for more than 30 days in a learning year` | `Exception` | `CompanyLearnersService.java -> calculateNewTerminationDate()` | Triggered when: Lost days < 31 |
| `This option is available to existing accredited Assessors or Moderators` | `Exception` | `AssesorModiratorUI.java -> doneUserBit()` | Triggered when: Unit standards is missing ) (+2 other variants (and other variations) |
| `This task is already with someone else.` | `Exception` | `UsersTaskUI.java -> taskRedirect()` | Triggered when: Task get task status( |
| `This user already exist as a contact person` | `Exception` | `CompanyUsersService.java -> contactPersonValidation()` | Triggered when: List is provided and list size( |
| `This user already exist as an Assessor/Facilitator` | `Exception` | `CompanyUsersService.java -> assfacilitatorValidation()` | Triggered when: List is provided and list size( |
| `Total vacancies filled can not be greater than total number of vacancies for the position.` | `Exception` | `WspSkillsRequirementsUI.java -> createAndAppendToListPast()` | Triggered when: Wsp skills requirements get total vacancies filled( |
| `Trade Test Type Error!!! Please contact administrator` | `Exception` | `ArplTradeTestLearnersUI.java -> requestTradeTestApplication() (+1 others)` | Triggered when: Company learners get legacy target class( |
| `Trade Test not a legacy!!! Please contact administrator` | `Exception` | `ArplTradeTestLearnersUI.java -> requestTradeTestApplication() (+1 others)` | Triggered when: Company learners get legacy target class( |
| `Trade already exist, please update` | `Exception` | `AppraisalsService.java -> createList()` | Triggered when: Entity id( |
| `Training Delivery Method code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get training delivery method code( |
| `Tranche payments linked to MOA. Please proceed to use project termination for this MOA.` | `Exception` | `ActiveContractsService.java -> checkIfCanWithDrawApplication()` | Triggered when: Active contract detail service count of active contract detail by active contract active contracts id( |
| `Unable To Assign Stakeholder Relations, contact support!` | `Exception` | `StakeholderRelationsSurveyUI.java -> stakeholderrelationssurveyInsert() (+1 others)` | Triggered when: Stakeholderrelations is missing \|\| stakeholderrelations id( |
| `Unable to apply. User must complete a mininum of 50% of total desiganted trade levels. Contact support!` | `Exception` | `CompanyLearnersService.java -> validateIfLearnerCanApplyLostTime()` | Triggered when: Percentage completed < 50 00 |
| `Unable to assigned status, contact support!` | `Exception` | `WorkplaceApproavlReportingUI.java -> addStatusForFilter() (+1 others)` | Triggered when: Add remove status is missing |
| `Unable to create lost time request at current level of designated trade. Contact Support!` | `Exception` | `CompanyLearnersService.java -> validateIfLearnerCanApplyLostTime()` | Triggered when: Dtl get extension( |
| `Unable to find link for upload, contact support!` | `Exception` | `WorkplaceMonitoringSiteVisitWorkflowUI.java -> prepGenericUpload()` | Triggered when: Generic upload object is missing |
| `Unable to linked provider application tp request Contact Support!` | `Exception` | `SDPExtensionOfScopeService.java -> requestExtensionOfScope()` | Triggered when: Sdpextensionofscope get training provider application( |
| `Unable to locate` | `Exception` | `WorkplaceMonitoringSiteVisitService.java -> initiateWorkplaceMonitoring()` | Triggered when: Get clo(entity get company( |
| `Unable to locate Administrator. Contact Support!` | `Exception` | `CompanyLearnersTradeTestService.java -> completeNambApprovalTradeTest()` | Triggered when: Users assigned to list size( |
| `Unable to locate CLO` | `Exception` | `SitesSmeService.java -> locateClrCrmBySme()` | Triggered when: Notify users size( |
| `Unable to locate CLO assigned to company, contact support!` | `Exception` | `WorkplaceMonitoringSiteVisitService.java -> rejectToClo()` | Triggered when: Clo user is missing \|\| clo user id( |
| `Unable to locate CLO for next process. Contact support!` | `Exception` | `WorkPlaceApprovalService.java -> locateCloForTask()` | Triggered when: Users count (+1 other variants (and other variations) |
| `Unable to locate CRM for next process. Contact support!` | `Exception` | `WorkPlaceApprovalService.java -> locateCrmForTask()` | Triggered when: Users size( |
| `Unable to locate CRM for the process, contact support!` | `Exception` | `WorkplaceMonitoringSiteVisitService.java -> submitNonComplianceForApproval()` | Triggered when: Crm list is empty( |
| `Unable to locate CRM user, contact support!` | `Exception` | `WorkplaceMonitoringSiteVisitService.java -> completeCloSignOff()` | Triggered when: Crm user is missing \|\| crm user id( |
| `Unable to locate Client Services Administrator, please contact support` | `Exception` | `CompanyLearnersTerminationService.java -> rejectCompanyLearners()` | Triggered when: To user list is missing \|\| to user list size( |
| `Unable to locate Company Communication, contact support!` | `Exception` | `SearchCompanyUI.java -> switchCompanyCommunicationActive()` | Triggered when: Company communication is missing \|\| company communication id( |
| `Unable to locate Contract, contract support!` | `Exception` | `DgAllocationService.java -> moaVerstionTwoByWspRetrunBytesByActiveContract()` | Triggered when: Activecontract get eletronic signoff( |
| `Unable to locate Coordinator For Region, contact support!` | `Exception` | `LegacyTradeTestRegistrationUI.java -> regionCheckCoordinator() (+2 others)` | Triggered when: Region user list size( |
| `Unable to locate Number of company employees. Contact support!` | `Exception` | `WspService.java -> wspSdfCheck() (+1 others)` | Triggered when: Wsp get company( |
| `Unable to locate Quality Assurance: Manager. Contact Support!` | `Exception` | `CompanyLearnersTradeTestService.java -> completeTaskArplRejection()` | Triggered when: Manager qa is missing \|\| manager qa size( |
| `Unable to locate Quality Assuror For Region, contact support!` | `Exception` | `LegacyTradeTestRegistrationUI.java -> regionCheckQualityAssuror() (+2 others)` | Triggered when: Region user list size( |
| `Unable to locate Recognition Agreement. Contact support!` | `Exception` | `WspService.java -> wspSdfCheck() (+1 others)` | Triggered when: Wsp get company( |
| `Unable to locate Region Client Service Admin. Contact Support!` | `Exception` | `CompanyLearnersTradeTestService.java -> completeNambApproval()` | Triggered when: Users assigned to list size( |
| `Unable to locate SDL number profile linked to the legacy accrediciation. Contact support!` | `Exception` | `SearchCompanyUI.java -> searchLegacyTpVersionTwo()` | Triggered when: Company is missing |
| `Unable to locate address information, contact support!` | `Exception` | `MonitorAddressInformationUI.java -> updateInformation()` | Triggered when: Selected address is missing and selected address id( |
| `Unable to locate assigned Region Client Services Administrator. Contact support!` | `Exception` | `CompanyLearnersTradeTestService.java -> reopenFromHoldingAreaWithMerSETAThree()` | Triggered when: Users assigned to list size( |
| `Unable to locate bulk item entry, contact support!` | `Exception` | `DgActiveContractsBulkApprovalWorkflowUI.java -> withdrawActiveContractsCompanyContact()` | Triggered when: Dg contracting bulk items is missing |
| `Unable to locate bulk item, contact support!` | `Exception` | `DgActiveContractsBulkApprovalWorkflowUI.java -> prepWithdrawOfApplication()` | Triggered when: Dg contracting bulk items is missing |
| `Unable to locate comapny information, contact support!` | `Exception` | `MonitorCompanyInformationUI.java -> updateCompanyInformation()` | Triggered when: Selected company is missing and selected company id( |
| `Unable to locate company assigned, contact support with accreditation number and user trying to access!` | `Exception` | `TrainingProviderApplicationService.java -> locateSdpTypeByApplicationAndSessionUser()` | Triggered when: Tpa get company( |
| `Unable to locate contract against bulk item, contact support!` | `Exception` | `DgActiveContractsBulkApprovalWorkflowUI.java -> prepWithdrawOfApplication()` | Triggered when: Dg contracting bulk items get active contracts( |
| `Unable to locate current designated trade level assigned to learner. Contact Support!` | `Exception` | `CompanyLearnersService.java -> validateIfLearnerCanApplyLostTime()` | Triggered when: Dtl is missing |
| `Unable to locate current level assigned to designated trade. Contact support!` | `Exception` | `CompanyLearnersService.java -> calculateNewTerminationDate()` | Triggered when: Dtl is provided |
| `Unable to locate external user email address, contact support!` | `Exception` | `SignoffService.java -> testExternalUserEmailSent()` | Triggered when: Signoff get temp signoff( |
| `Unable to locate external user information, contact support!` | `Exception` | `SignoffService.java -> testExternalUserEmailSent()` | Triggered when: Signoff get temp signoff( |
| `Unable to locate extract script. Contact Support!` | `Exception` | `DhetReportingService.java -> findByDhetFileNumberEnumReturnSql()` | Triggered when: Dhet reporting is provided and dhet reporting id( |
| `Unable to locate fin year For DG Allocation Forecast Report. Report Generation Failed.` | `Exception` | `ReportGenerationService.java -> runReportgeneration()` | Triggered when: Report generation schedule get fin year( |
| `Unable to locate learner assigned for next task, contact support!` | `Exception` | `CompanyLearnersService.java -> companyApproval()` | Triggered when: Company learners transfer get company learners( |
| `Unable to locate learner's readiness date, contact support!` | `Exception` | `CompanyLearnersService.java -> validiationOnTradeTestRequest()` | Triggered when: Company learners trade test get learner readiness date( |
| `Unable to locate level selected. Contact Support!` | `Exception` | `LearnersUI.java -> setDesigantedTradeLevel() (+2 others)` | Triggered when: Selected designated trade level is missing |
| `Unable to locate levels assigned to designated trade. Contact support!` | `Exception` | `CompanyLearnersService.java -> calculateNewTerminationDate()` | Triggered when: Not designated trade level list is empty( |
| `Unable to locate link. Contact support!` | `Exception` | `SdpManagementWithSitesUI.java -> viewProviderApplicationInformation()` | Triggered when: Sdp company link is missing |
| `Unable to locate linked provider application. Contact support!` | `Exception` | `SDPExtensionOfScopeService.java -> requestNonMerSETAExtensionOfScope()` | Triggered when: Sdpextensionofscope get training provider application( |
| `Unable to locate look up information for: Quarter One. Please review the configuration.` | `Exception` | `QmrFinYearsService.java -> generateNewFinYear()` | Triggered when: Quarter one look up is missing \|\| quarter one look up get from date( |
| `Unable to locate next user, contact support!` | `Exception` | `CompanyLearnersTradeTestService.java -> completeTaskTradeTestNambApproval()` | Triggered when: Recivers size( |
| `Unable to locate notice, contact support!` | `Exception` | `StakeholderRelationsUI.java -> sendNotification()` | Triggered when: Stakeholderrelations is provided |
| `Unable to locate notification recivers. Please ensure SDP contact persons are assigned or contact merSETA support.` | `Exception` | `AssessorModeratorCompanySitesService.java -> removeLinkSendNotification()` | Triggered when: Notification recivers is empty( |
| `Unable to locate provider application, contact support!` | `Exception` | `ApprovedLegacyProviderApplicationsUI.java -> prepAlteration()` | Triggered when: Training provider application is missing |
| `Unable to locate qualification assigned, contact support` | `Exception` | `CompanyLearnersService.java -> workplaceApprovalAccreditationChecksOld() (+1 others)` | Triggered when: Not isworkplaceapproved |
| `Unable to locate qualification selected for test center selection. Contact support!` | `Exception` | `ViewCreateLegacyArplUI.java -> findProviderApplicationsAssignedToQualification() (+4 others)` | Triggered when: Company learners trade test is provided and company learners trade test get qualification( |
| `Unable to locate rejection reasons. Notification failed!` | `Exception` | `WspService.java -> sendReleventNotificationToUsersAdmin() (+1 others)` | Triggered when: Reject reasons is missing |
| `Unable to locate report selected, contact support` | `Exception` | `TradeTestStageReportingUI.java -> viewResults() (+3 others)` | Triggered when: Selected collection enum is missing ) (+1 other variants (and other variations) |
| `Unable to locate request users, contact support!` | `Exception` | `CompanyLearnersService.java -> requestTransfer()` | Triggered when: User list size( |
| `Unable to locate search type, contact support!` | `Exception` | `SitesSmeUI.java -> searchForUser()` | Triggered when: Service count of by rsa id or passort not deactivated company, this passport number, false, approval enum deactivated |
| `Unable to locate status, contact support!` | `Exception` | `CompanyLearnersTradeTestService.java -> approveApplicationWithWithMersetaTwoTradeTest()` | Triggered when: Update list size( |
| `Unable to locate trade test center accrediciation. Contact support!` | `Exception` | `LearnersUI.java -> setTradeTestCenterAndRegionCheck() (+2 others)` | Triggered when: App list is empty( |
| `Unable to locate user for sign notification of one time pin! contact support!` | `Exception` | `ActiveContractsService.java -> sendOneTimePinForSignOffSms()` | Triggered when: Signoff get user( |
| `Unable to locate user information, contact support!` | `Exception` | `MonitorUserInformationUI.java -> updateInformation()` | Triggered when: Selected user is missing and selected user id( |
| `Unable to locate user when required for company region reporting. Contact support!` | `Exception` | `CompanyService.java -> populateDataForCompanyRegionReport()` | Triggered when: Session user is missing |
| `Unable to locate users for next task, contact support!` | `Exception` | `CompanyLearnersService.java -> learnerApproval()` | Triggered when: User list size( |
| `Unable to locate users for sign off of MOA, contact support!` | `Exception` | `ActiveContractsService.java -> completeWorkflowES()` | Triggered when: Signoffs size( |
| `Unable to locate what link must be created. Contact Support!` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | General system failure or invalid processing state |
| `Unable to proceed, please await notification on outcome` | `Exception` | `ActiveContractsUI.java -> extensionTerminationUnderwayValidiation()` | Triggered when: Activecontracts get extension termination workflow active( |
| `Unable to proceed, please wait till date learner is ready to attempt test.` | `Exception` | `CompanyLearnersService.java -> validiationOnTradeTestRequest()` | Triggered when: Company learners trade test get learner readiness date( |
| `Unfortuabltely this LEVY file is not in the correct format. It's a self exrtacting zip file. Please unzip the files and zip it into the corrext format.` | `Exception` | `SarsLoadLevies.java -> checkIfDHETdidOnlyEXEFileType()` | Triggered when: Onlyexe |
| `Unit Standard Code is required` | `Exception` | `MandatoryGrantDetailService.java -> validateCSVDataVersionTwo()` | Triggered when: A get qualification code( |
| `Unit Standard Error` | `Exception` | `MonitorLegacyAssessorModUI.java -> updateUnitStandards()` | Triggered when: User unit standard is missing ) (+1 other variants (and other variations) |
| `Unit Standard already added` | `Exception` | `MonitorVerificationLearnersUI.java -> preCheckUnitStandards()` | Triggered when: Summative assessment report unit standards service count of unit standards summative assessment report unit standards get unit standards( |
| `Unit Standard already exist in the list` | `Exception` | `AmDetailUI.java -> addExtensionOfScopeUnitStandard()` | Triggered when: Uus get unit standard( |
| `Unit Standard already in the list` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfUsAdded() (+2 others)` | Triggered when: Unit standard matches cus get unit standard( |
| `Unit Standard not found` | `Exception` | `LegacyLearnersUI.java -> redirectLegacyUnitStandard() (+1 others)` | Triggered when: Legacyunitstandard get unit standard( |
| `Unit Standards has already been added for this user` | `Exception` | `UserUnitStandardService.java -> createAUserUnitStandard()` | Triggered when: Find by unit standard and ap application(unit standards id( |
| `Unit Standards not the same` | `Exception` | `VerificationLearnersUI.java -> checkIfSameQualification()` | Triggered when: Trainingproviderverfication get company learners( |
| `Unit Standart Not Available` | `Exception` | `SkillsRegistrationService.java -> sendLPM_TP_001_RejectionEmail()` | Triggered when: Unit standards size( |
| `Unit Standrd Already Assigned To Learnership` | `Exception` | `QualificationUI.java -> addUnitStandard()` | Triggered when: Qualification unit standards service count of by unit standard and qualification qualification unit standards get unit standards( |
| `Unit standard already assigned to application. Please select a different unit standard.` | `Exception` | `LegacyProviderApplicationAlterationAuditService.java -> createNewLinkAndAudit()` | Triggered when: Company unit standard service count of unit standard target class and key training provider application class get name( |
| `Unit standard credits error!!! Please contact your administrator` | `Exception` | `TrainingProviderVerficationService.java -> checkCredits()` | Triggered when: Companylearners learnership (+3 other variants (and other variations) |
| `Unit standard not found` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> runInit()` | Triggered when: Legacyunitstandard get unit standard( |
| `Unit standards have already been added` | `Exception` | `MonitorVerificationLearnersUI.java -> summativeassessmentreportUpdate()` | Triggered when: List summative assessment report unit standards size( |
| `Upload ID document` | `Exception` | `ProfileUI.java -> createTaskToUpdateProfile()` | Triggered when: Change reason get doc( |
| `Upload Supporing Document Before Proceeding` | `Exception` | `DgContractingBulkEntryService.java -> requestTerminationOfContract() (+1 others)` | Triggered when: Doc is missing \|\| doc data (+1 other variants (and other variations) |
| `Upload all documents before proceeding` | `Exception` | `DgVerificationService.java -> sdfAppealApplicationTaskNotification()` | Triggered when: Doc id( |
| `Upload suporting documents` | `Exception` | `SDPExtensionOfScopeUI.java -> addTPSkillsProgreme() (+3 others)` | Triggered when: Doc parent get doc( |
| `Upload supporting documents to proceed.` | `Exception` | `TrainingProviderApplicationUI.java -> submitUpdateDesignationSdp()` | Triggered when: Action doc list is empty( |
| `User Already Assigned to Qualification Development Team` | `Exception` | `QdfCompanyUsersService.java -> checkUserAssignedToQdfCompany()` | Triggered when: Amount not = 0 |
| `User already added to the meeting` | `Exception` | `ReviewCommitteeMeetingUI.java -> addUser()` | Triggered when: Not meeting users list contains(meeting user |
| `User already assigned as company contact. Please provide a different user.` | `Exception` | `TrainingProviderUI.java -> addNewSdpLink()` | Triggered when: Sdp company service count of user assigned by training site id assigned training site id (+1 other variants (and other variations) |
| `User already assigned to company with type. Please select a different user or type.` | `Exception` | `TrainingProviderApplicationUI.java -> createNewAssessorModeratorLink() (+1 others)` | Triggered when: Assessor moderator company sites service count of by user training site ass mod type new user assigned id (+3 other variants (and other variations) |
| `User already assigned. Please select a different user or application type.` | `Exception` | `TrainingProviderUI.java -> addFacilitatorAssessorVersionTwo()` | Triggered when: Alread in list |
| `User and Department needs to be selected` | `Exception` | `ChatMessageService.java -> findRootChatsForUserAndDepartment()` | Triggered when: User is missing \|\| hosting company departments is missing |
| `User does not exist` | `Exception` | `LegacyPersonService.java -> populateLegacyUser()` | Triggered when: Legacy person get nationality desc( |
| `User does not have ID and Passport Number` | `Exception` | `LegacyPersonService.java -> populateLegacyUser()` | Triggered when: Legacy person get alternate id no( |
| `User information errorr` | `Exception` | `CompanyLearnersDetailsChangeService.java -> submitLearnerUpdate()` | Triggered when: User is provided |
| `User is already allocated for sign off` | `Exception` | `WorkPlaceApprovalUI.java -> addToList() (+2 others)` | Triggered when: Signoffloop get user( |
| `User with ID number is already linked to the company` | `Exception` | `SitesSmeUI.java -> searchForUser()` | Triggered when: Service count of by rsa id or passort not deactivated company, this idnumber, true, approval enum deactivated |
| `User with Passport Number is already linked to the company` | `Exception` | `SitesSmeUI.java -> searchForUser()` | Triggered when: Service count of by rsa id or passort not deactivated company, this passport number, false, approval enum deactivated |
| `User with this ID Number does not exist` | `Exception` | `LearnerRegistrationFormUI.java -> callBackMethod() (+5 others)` | Triggered when: Companylearners is provided and company learners service get company learner qualification(companylearners) (+1 other variants (and other variations) |
| `User with this ID Number is deceased as per Department of Home Affairs records` | `Exception` | `LegacyLearnerRegistrationFormUI.java -> callBackMethod() (+6 others)` | Triggered when: Splited length > 1) (+4 other variants (and other variations) |
| `Users with same email or ID number already exist` | `Exception` | `QdfCompanyService.java -> updateCreateRegisterUser()` | Triggered when: T is provided |
| `Valdiation Exception. Refer to message displayed.` | `Exception` | `ViewCreateLegacyArplUI.java -> checkIfQualificationObtainedByLearner() (+1 others)` | Triggered when: Validation failed |
| `Validiation Expection: Please Ensure From Date Selection Is Before To Date Selection` | `Exception` | `SarsReportingUI.java -> dateValidiations()` | Triggered when: From date selection after(to date selection |
| `Validiation Expection: Please Ensure From Scheme Year Selection Is Before The To Scheme Year Selection` | `Exception` | `SarsReportingUI.java -> schemeyearSelectionValidiation()` | Triggered when: From scheme year > to scheme year |
| `Validiation Expection: Please Ensure To Date Selection Is After From Date Selection` | `Exception` | `SarsReportingUI.java -> dateValidiations()` | Triggered when: To date selection before(from date selection |
| `Validiation Expection: Please Ensure To Scheme Year Selection Is After From Selection` | `Exception` | `SarsReportingUI.java -> schemeyearSelectionValidiation()` | Triggered when: To scheme year < from scheme year |
| `Validiation Expection: Provide Arrival From and To Date Before Proceeding.` | `Exception` | `SarsReportingUI.java -> dateValidiations()` | Triggered when: From date selection is missing \|\| to date selection is missing |
| `Validiation Expection: Provide Scheme Year From and To Selection Before Proceeding.` | `Exception` | `SarsReportingUI.java -> schemeyearSelectionValidiation()` | Triggered when: From scheme year is missing \|\| to scheme year is missing |
| `Validiation Type Already Assigned. Please Select A Different Validiation.` | `Exception` | `WorkplaceMonitoringActionPlanService.java -> createLookUpEntry()` | Triggered when: Count by action plan validiation type and does not equal id(entity action plan validiation type enum (+1 other variants (and other variations) |
| `Vendor does not exist on GP` | `Exception` | `BankingDetailsService.java -> updateBankningDetailsOnGP()` | Triggered when: Vendor is provided ) (+1 other variants (and other variations) |
| `VendorID cannot be empty` | `ValidationException` | `GetVendorByKeyAdapter.java -> validate()` | Triggered when: This vendor id is missing \|\| this vendor id trim( |
| `Verification Qualification Error!!! Please contact your administrator` | `Exception` | `VerificationLearnersUI.java -> checkIfSameQualification()` | Triggered when: Trainingproviderverfication get company learners( |
| `Window Already Closed` | `Exception` | `InitiatewspDgUI.java -> saveWSPSignoff()` | Triggered when: Get now( |
| `WorkPlace Approval Error` | `Exception` | `CompanyInfoUI.java -> createMentorWorkPlaceApproval()` | Triggered when: Work place approval is provided |
| `WorkPlace Approval has not been approved yet` | `Exception` | `LearnerRegistrationFormUI.java -> continueRegistration() (+3 others)` | Triggered when: Work place approval is provided and work place approval get approval enum( |
| `Wrong ID Type` | `Exception` | `EmployeesImportService.java -> validate()` | Triggered when: Id passport enum id passport enum by value( integer value of(ei id type( |
| `Year already assigned, please provide a different year` | `Exception` | `SarsLevySchemeYearReturnsService.java -> validiateInformation() (+1 others)` | Triggered when: Count by year(sars levy scheme year returns for scheme year (+3 other variants (and other variations) |
| `You are accreditated for this  Unit Standard, please select a different Unit Standard` | `Exception` | `AssessorModeratorApplicationService.java -> validateUniStandardAccreditatation()` | Triggered when: Assessor moderator application list get(0 |
| `You are accreditated for this qualification, please select a different qualification` | `Exception` | `AssessorModeratorApplicationService.java -> validateQualificationAccreditatation()` | Triggered when: Assessor moderator application list get(0 |
| `You are already accredited for this Skills Programme` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfSkillsProgAdded()` | Triggered when: Tp splist is provided and tp splist count (+1 other variants (and other variations) |
| `You are already accredited for this Skills Set` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfSkillsSetAdded()` | Triggered when: Count>0 |
| `You are already accredited for this Unit Standard` | `Exception` | `SDPExtensionOfScopeUI.java -> checkIfUsAdded()` | Triggered when: Count>0 |
| `You are already accredited for this qualification` | `Exception` | `SDPExtensionOfScopeUI.java -> addQualificationToList()` | Triggered when: Company qualifications is provided ) (+1 other variants (and other variations) |
| `You are not registered on the system. Please register` | `Exception` | `RegisterService.java -> resendChecks()` | Triggered when: Is blank: db user get email( |
| `You can not action this task before the site visit date` | `Exception` | `LearnerRegistrationFormUI.java -> validateSiteVisit() (+1 others)` | Triggered when: Meeting date after(current date |
| `You can only withdraw at DG MOA from this section. Please select a DG MOA to use this functionlaity.` | `Exception` | `ActiveContractsService.java -> checkIfCanWithDrawApplication()` | Triggered when: Active contracts get moa type( |
| `You cannot change the type to document since this is a chapter with other documents under it!` | `Exception` | `HostingCompanyEmployeeTemplatesService.java -> preUpdateChecks() (+1 others)` | Triggered when: L is provided and l size( |
| `You currently do not have access to action` | `Exception` | `SDPExtensionOfScopeUI.java -> validiateCanEditInformation()` | Triggered when: Sdp type is missing \|\| sdp type get action sdp information( |
| `You currently do not have access to view the information` | `Exception` | `TrainingProviderApplicationUI.java -> validiateCanViewInformation() (+1 others)` | Triggered when: Sdp type is missing \|\| sdp type view trade test centre assessors (+1 other variants (and other variations) |
| `You dont have any contacts to sign off banking details` | `Exception` | `BankingDetailsService.java -> createNoUpdate()` | Triggered when: Cu size( |
| `You have exceeded the number of learners allocated for this intervention as per the project implementation plan` | `Exception` | `CompanyLearnersService.java -> createLearner() (+1 others)` | Triggered when: Count >= (cl get project implementation plan( |
| `You have not changed any of the interventions` | `Exception` | `DgAllocationAdditionalUI.java -> changeAllocation() (+1 others)` | Triggered when: Not change made |
| `You have reached maximum number of attempts` | `Exception` | `CompanyLearnersTradeTestService.java -> requestARPLTradeTestApplicationForLegacy()` | Triggered when: Num >= 3 |
| `You have reached maximum number of attemts` | `Exception` | `CompanyLearnersService.java -> requestARPLTradeTestApplication() (+1 others)` | Triggered when: List count (+1 other variants (and other variations) |
| `You need to specify the Vendor Class ID` | `ValidationException` | `CreateVendorAdapter.java -> validate()` | Triggered when: This vendor get class key( |
| `Your Non Credit Bearing Title is too long` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get non credid bearing description( |
| `Your application cannot be processed because all your qualifications have expired` | `Exception` | `AssesorModiratorUI.java -> loadAssessorLegacyQualification() (+1 others)` | Triggered when: Not at least one qual to be processed |
| `Your email address has already been confirmed.` | `Exception` | `RegisterService.java -> resendChecks()` | Triggered when: Db user email confirm date (+1 other variants (and other variations) |
| `Your new commencement date cannot be the same as your current commencement date` | `Exception` | `CompanyLearnersService.java -> validateLearnerChangeOld() (+1 others)` | Triggered when: Company learners change get company learners( |
| `Your reason is too long` | `Exception` | `CompanyInfoUI.java -> doChange()` | Triggered when: Change reason get description( |
| `company passed can not be null` | `Exception` | `WspCompanyHistoryCreateService.java -> run()` | Triggered when: Thread company is missing \|\| thread company id( |
| `company.registration.list.validation.error` | `ValidationException` | `CompanyService.java -> createCompanyAndSendTask() (+1 others)` | Triggered when: Create task) (+1 other variants (and other variations) |
| `email.not.on.system` | `ValidationException` | `LogonService.java -> logonByEmail()` | Triggered when: U is missing |
| `invalid.password.id` | `ValidationException` | `LogonService.java -> changePassword()` | Triggered when: Not password encryptor check password(password trim( |
| `invalid.password.profile` | `ValidationException` | `LogonService.java -> logonByEmail()` | Triggered when: Not password encryptor check password(input password trim( |
| `not.active.profile` | `ValidationException` | `LogonService.java -> logonByEmail()` | Triggered when: U active (+1 other variants (and other variations) |
| `not.confirmed.email.check.mail` | `ValidationException` | `LogonService.java -> logonByEmail()` | Triggered when: U get status( |
| `not.convert.to.currency` | `ValidationException` | `CurrencyService.java -> exhangeRateFromTo()` | Triggered when: From currency is missing \|\| to currency is missing |
| `qualification.already.exists` | `ValidationException` | `UserQualificationsService.java -> create() (+2 others)` | Triggered when: Entity id( |
| `supply.amount.amount.bean` | `ValidationException` | `CurrencyService.java -> convert()` | Triggered when: Amount bean get amount( |
| `supply.locale.amount.bean` | `ValidationException` | `CurrencyService.java -> convert()` | Triggered when: Amount bean get from locale( |
| `target class can not be null or empty` | `Exception` | `WspCompanyHistoryCreateService.java -> run()` | Triggered when: Thread target class is missing \|\| thread target class is empty( |
| `target key can not be null` | `Exception` | `WspCompanyHistoryCreateService.java -> run()` | Triggered when: Thread target key is missing |
| `unit.standard.already.exists` | `ValidationException` | `CompanyUnitStandardService.java -> create()` | Triggered when: Entity id( |
| `user.already.email` | `ValidationException` | `UsersService.java -> update()` | Triggered when: Check email used(entity get email( |
| `user.email.not.registered` | `ValidationException` | `LogonService.java -> changePassword()` | Triggered when: U is missing ) (+1 other variants (and other variations) |
| `wsp.fin.year.exists` | `ValidationException` | `WspService.java -> checkWspFinYearUnique() (+1 others)` | Triggered when: Wsp count is greater than zero ) (+1 other variants (and other variations) |
