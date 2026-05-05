# Implementation Tasks for Spring Boot ToS Analyzer

This file tracks the phased implementation of the Spring Boot application for analyzing Terms of Service text. Use checkboxes to mark completed tasks.

## Phase 1: MVP (Text-Paste Analysis)
**Goal**: Working app for pasting ToS text and getting structured red-flag reports.

- [x] Project Setup (1-2 hours): Create Spring Boot project with Maven. Add dependencies (`spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `spring-ai-starter-model-openai`). Configure OpenAI API key in `application.properties`.
- [x] Controller Layer (1-2 hours): Implement `@Controller` with `@PostMapping` for form submission. Validate input length (max 10,000 chars).
- [x] Analysis Service (3-4 hours): Create service class. Build prompt for structured JSON output. Use Spring AI's `ChatClient` for GPT-4o mini call. Parse response into DTOs. Include simple baseline comparison logic.
- [ ] Web Layer (2-3 hours): Create Thymeleaf templates (`index.html` for form, `results.html` for output). Add Bootstrap for basic styling (severity badges, highlighted excerpts).
- [ ] Presentation Layer (1-2 hours): Render results in Thymeleaf with rankings, highlights, and disclaimer.
- [ ] Testing & Validation (2-3 hours): Test with 3+ sample ToS texts. Ensure response <30s, structured flags, no errors. Manual review for relevance.
- [ ] Overall Phase 1 Success: App runs locally, processes samples accurately.

## Phase 2: Better Analysis (Deferred)
**Goal**: Enhance for longer documents, add PDF support, persistence, expanded baselines.

- [ ] Chunking Logic (2-3 hours): Modify service to split long texts (e.g., by paragraphs, 1000 tokens) and aggregate AI results.
- [ ] PDF Support (3-4 hours): Add Apache Tika dependency. Create upload endpoint, extract text, pass to analysis.
- [ ] Baseline Expansion (2-3 hours): Expand hardcoded baseline clauses from market sources (e.g., EFF templates).
- [ ] Persistence (2-3 hours): Add H2 DB, entity for scans, save results.
- [ ] UI Updates (1-2 hours): Add file upload button, history page.
- [ ] Testing (2-3 hours): Validate with PDFs, long docs, history view.
- [ ] Overall Phase 2 Success: Handles complex docs, accurate baselines, persistence works.

## Phase 3: Productization (Deferred)
**Goal**: Add user accounts, reports, billing for full product.

- [ ] Auth & Accounts (5-7 hours): Add Spring Security, user entities, login/signup.
- [ ] Reports & Sharing (3-5 hours): Generate saved/shareable reports (e.g., PDFs, links).
- [ ] Billing (4-6 hours): Integrate Stripe for API usage tracking.
- [ ] Vector DB (5-7 hours): Add PGVector/Supabase for baseline retrieval.
- [ ] Deployment Migration (2-3 hours): Migrate to paid host (e.g., Railway with billing).
- [ ] Overall Phase 3 Success: Multi-user app with monetization, deployed.