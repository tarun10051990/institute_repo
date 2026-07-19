export interface AuthResponse {
  token: string;
  email: string;
  firstName: string;
  lastName: string;
  userId: number;
  role: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phone?: string;
  educationLevel?: string;
  schoolName?: string;
  city?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface Category {
  id: number;
  name: string;
  code: string;
  description: string;
  icon: string;
  displayOrder: number;
  timeLimitMinutes: number;
  totalQuestions: number;
  questionCount: number;
}

export interface AnswerOption {
  id: number;
  optionText: string;
  optionLabel: string;
  displayOrder: number;
}

export interface Question {
  id: number;
  questionText: string;
  questionType: string;
  difficultyLevel: string;
  displayOrder: number;
  categoryId: number;
  categoryName: string;
  options: AnswerOption[];
}

export interface Session {
  id: number;
  sessionCode: string;
  status: string;
  startedAt: string;
  completedAt: string;
  createdAt: string;
  totalQuestions: number;
  answeredQuestions: number;
}

export interface CategoryScore {
  categoryId: number;
  categoryName: string;
  categoryCode: string;
  icon: string;
  rawScore: number;
  maxScore: number;
  percentage: number;
  traitSummary: string;
}

export interface CareerRecommendation {
  id: number;
  careerTitle: string;
  careerDescription: string;
  matchPercentage: number;
  careerField: string;
  requiredEducation: string;
  salaryRange: string;
  growthOutlook: string;
  rankOrder: number;
}

export interface MbtiOption {
  label: string;
  text: string;
  letter: string;
}

export interface MbtiQuestion {
  id: number;
  dimension: string;
  questionText: string;
  options: MbtiOption[];
}

export interface MbtiAnswer {
  questionId: number;
  letter: string;
}

export interface MbtiDimensionScore {
  dimension: string;
  leftLetter: string;
  leftName: string;
  rightLetter: string;
  rightName: string;
  leftCount: number;
  rightCount: number;
  chosenLetter: string;
  chosenName: string;
  strengthPercentage: number;
  description: string;
}

export interface MbtiResult {
  type: string;
  nickname: string;
  summary: string;
  overview: string;
  dimensions: MbtiDimensionScore[];
  strengths: string[];
  weaknesses: string[];
  careers: string[];
  relationships: string;
  growthTips: string;
}

export interface TraitScore {
  code: string;
  name: string;
  percentage: number;
}

export interface ActionPlanPhase {
  period: string;
  items: string[];
}

export interface Grade8Report {
  studentProfile: string;
  multipleIntelligence: TraitScore[];
  multipleIntelligenceSummary: string;
  learningStyle: TraitScore[];
  learningStyleName: string;
  learningStyleSummary: string;
  topStrengths: string[];
  developmentAreas: string[];
  recommendedSubjects: string[];
  competitions: string[];
  skillDevelopmentPlan: string[];
  careerClusters: string[];
  parentGuidance: string[];
  counsellorRecommendations: string[];
  actionPlan: ActionPlanPhase[];
}

export interface AssessmentResult {
  sessionId: number;
  sessionCode: string;
  userName: string;
  email: string;
  completedAt: string;
  categoryScores: CategoryScore[];
  careerRecommendations: CareerRecommendation[];
  overallSummary: string;
  mbti?: MbtiResult;
  report?: Grade8Report;
}

export interface AdminUser {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  role: string;
  city?: string;
  educationLevel?: string;
  createdAt: string;
  sessionCount: number;
  completedCount: number;
}

export interface AdminSession {
  sessionId: number;
  sessionCode: string;
  status: string;
  userId: number;
  userName: string;
  email: string;
  mbtiType?: string;
  completedAt?: string;
  createdAt?: string;
}

export interface MbtiOptionAdmin {
  id?: number;
  label: string;
  text: string;
  letter: string;
  displayOrder?: number;
}

export interface MbtiQuestionAdmin {
  id?: number;
  dimension: string;
  questionText: string;
  displayOrder?: number;
  active?: boolean;
  options: MbtiOptionAdmin[];
}

export interface MbtiProfileAdmin {
  id: number;
  typeCode: string;
  nickname: string;
  summary: string;
  overview: string;
  strengths: string[];
  weaknesses: string[];
  careers: string[];
  relationships: string;
  growthTips: string;
}
