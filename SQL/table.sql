DROP TABLE IF EXISTS public.student CASCADE;
DROP TABLE IF EXISTS public.teacher CASCADE;
DROP TABLE IF EXISTS public.course CASCADE;
DROP TABLE IF EXISTS public.enrollment CASCADE;
DROP TABLE IF EXISTS public.score CASCADE;
DROP TABLE IF EXISTS public.assignment CASCADE;
DROP TABLE IF EXISTS public.user CASCADE;
CREATE table if not exists public.student(
	student_id SERIAL primary key,
    id Integer NOT NULL,
    name VARCHAR(255) NOT NULL,
    sis_user_id VARCHAR(255) NOT NULL,
    login_id VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
	course_id INTEGER NOT NULL,
    email VARCHAR(255),
    UNIQUE(id, type)
);

CREATE table if not exists public.teacher(
    teacher_id SERIAL primary key,
	id Integer NOT NULL,
    name VARCHAR(255) NOT NULL,
    sis_user_id VARCHAR(255) NOT NULL,
    login_id VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
	course_id INTEGER NOT NULL,
    email VARCHAR(255),
    UNIQUE(id, type)
);

CREATE table if not exists public.course(
	course_id SERIAL primary key,
    id Integer NOT NULL,
    name VARCHAR(255) NOT NULL,
    course_code VARCHAR(255) NOT NULL,
	user_id INTEGER NOT NULL,
	UNIQUE(id, user_id)
);

CREATE table if not exists public.enrollment(
    id serial primary key,
    type Varchar(255) NOT NULL,
    user_id Integer NOT NUll,
    course_id INTEGER NOT NULL,
    enrollment_state VARCHAR(255) NOT NULL,
    UNIQUE(course_id, user_id, type)
	
);

CREATE table if not exists public.score(
    id INTEGER primary key,
    score DECIMAL,
    assignment_id INTEGER NOT NULL,
    user_id INteger NOT NULL,
    submitted_at TIMESTAMP WITH TIME ZONE,
    excused BOOLEAN,
    late BOOLEAN NOT NULL,
    missing BOOLEAN NOT NULL,
    entered_score DECIMAL,
	course_id INTEGER NOT NULL,
	participation_score_weight Numeric,
	participation_score_complete Numeric
);

CREATE table if not exists public.assignment(
    id Integer primary key,
    id Integer primary key,
    description VARCHAR(500),
    due_at TIMESTAMP WITH TIME ZONE,
    unlock_at TIMESTAMP WITH TIME ZONE,
    lock_at TIMESTAMP WITH TIME ZONE,
    points_possible DECIMAL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    published BOOLEAN NOT NULL,
    course_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    participation_weighting Integer DEFAULT 60,
	type VARCHAR(255),
    method VARCHAR(255),
	default_participation_weighting Integer DEFAULT 60
    );

CREATE table if not exists public.user(
    id Integer primary key,
    name VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(2048) NOT NULL,
    last_name VARCHAR(128) NOT NULL, 
    first_name VARCHAR(128) NOT NULL, 
    token VARCHAR(2048) NOT NULL 
);
