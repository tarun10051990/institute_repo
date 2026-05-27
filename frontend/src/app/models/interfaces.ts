export interface AuthResponse {
  token: string;
  email: string;
  firstName: string;
  lastName: string;
  userId: number;
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

export interface AssessmentResult {
  sessionId: number;
  sessionCode: string;
  userName: string;
  email: string;
  completedAt: string;
  categoryScores: CategoryScore[];
  careerRecommendations: CareerRecommendation[];
  overallSummary: string;
}
