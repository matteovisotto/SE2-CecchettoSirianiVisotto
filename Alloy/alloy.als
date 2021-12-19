//Signatures

sig User {
	name: one Name,
	surname: one Surname,
	email: one Email,
	dateOfBirth: one Date,
	areaOfResidence: one AreaOfResidence,
}

sig Name {}

sig Surname {}

sig Email {}

sig Date {}

sig AreaOfResidence {}


sig PolicyMaker extends User {
	policyMakerId: one PolicyMakerId,
}

sig PolicyMakerId {}

sig Post {
	text: one Text,
	creator: one User,
	timestamp: one DateTime,
	attachment: lone Attachment,
	status: one Status,
	visibility: one Visibility,
}

sig Text {}

sig DateTime in Int {}

abstract sig Status {}

sig PENDING extends Status{}

sig ACCEPTED extends Status {}

sig REJECTED extends Status {}

sig Attachment {}

sig Discussion {
	title: one Title,
	text: one Text,
	timestamp: one DateTime,
	creator: one PolicyMaker,
	posts: some Post,
}

sig Title {}

sig Topic {
	title: one Title,
	timestamp: one DateTime,
	discussions: some Discussion,
}

sig Administrator {
	email: one Email,
	password: one Password,
}

sig Password {}
{ //Each password is associated to an Administrator
	all p : Password | (some a: Administrator | a. password = p)
}

sig DataSource {
	name: one Name,
	source: one Source,
	description: lone Description,
	dataType: one DataType,
}

sig Source {}

sig Description {}

sig DataType {
	name: one Name,
}

abstract sig Visibility {}

sig Visible extends Visibility {}

sig Invisible extends Visibility {}

-----------------------------------------------------------------------------------------------------------------
//Facts

fact { //Each policyMakerId is unique
	no disj p1, p2: PolicyMaker | p1.policyMakerId = p2.policyMakerId
}

fact { //Each User has an unique email
	no disj u1, u2 : User | u1.email = u2.email
}

fact { //Each Administrator has an unique email
	no disj a1, a2 : Administrator | a1.email = a2.email
}



fact { //If a post exist, it must be PENDING, ACCEPTED or REJECTED
	all p: Post | p.status = PENDING or p.status = ACCEPTED or p.status = REJECTED
}

fact { //Two Users can not be creators of the same Post
	all p: Post | (no disj u1, u2: User | (u1 = p.creator and u2 = p.creator and u1 != u2))
}

fact { //Two Policy makers can not be creators of the same Discussion
	all d: Discussion | (no disj p1, p2: PolicyMaker | (p1 = d.creator and p2 = d.creator and p1 != p2))
}

fact { //A Post always belong to one Discussion
	all p: Post | one d: Discussion | p in d.posts
}

fact { //A Discussion always belong to one Topic
	all d: Discussion | one t: Topic | d in t.discussions
}

fact { //A Discussion has always a title
	all d: Discussion | one t: Title | d.title = t
}

fact { //A Topic has always a title
	all to: Topic | one ti: Title | to.title = ti
}

fact { //A Post can not exists without a creator
	all p: Post | one u: User | u = p.creator
}

fact { //A Discussion can be created only by a Policy maker
	all d: Discussion | one p: PolicyMaker | p = d.creator and p.policyMakerId != none
}

fact { //There can not exist a Post without Text and without Attachment
	no disj p: Post | p.text = none and p.attachment = none
}

fact { //A Data source can not exist without a source
	all d: DataSource | one s: Source | d.source = s
}

/*
fact { //A Policy maker could have created more than one Discussion
	all p: PolicyMaker | some d: Discussion | p.userUid = d.creatorId
}*/

fact { //A Post is not visible if it has been rejected or is still in the pending list
	all p: Post | (p.status = PENDING or p.status = REJECTED) implies p.visibility = Invisible
}

fact { //A Post is visible if it has been accepted
	all p: Post | p.status = ACCEPTED implies p.visibility = Visible
}
/*
fact { //A text exist only if it's present a Discussion or a Post
	all t: Text | one d: Discussion, p: Post | d.text = t or p.text = t
}

fact { //A title exist only if it's present a Discussion or a Topic
	all t: Title | one d: Discussion, to: Topic | d.title = t or to.title = t
}
*/
fact { //An attachment exist only if it's present a Post
	all a: Attachment | one p: Post | p.attachment = a
}

fact { //A description exist only if it's present a DataSource
	all d: Description | one ds: DataSource | ds.description = d
}

fact { //A name exist only if it's present an User
	all n: Name | one u: User | u.name = n
}

fact { //A surname exist only if it's present an User
	all s: Surname | one u: User | u.surname = s
}

fact { //A source exist only if it's present a DataSource
	all s: Source | one ds: DataSource | ds.source = s
}


/*
fact { //An email exist only if it's present an User or an Administrator
	all e: Email | one u: User | not (u.email = e) implies (one a:Administrator | a.email = e)
}*/

//A policyMakerId could exist even if it's not present a Policy

-----------------------------------------------------------------------------------------------------------------
//Assertions


/*
// G_i: Allow a User to publish a Post
assert publishAPost {
	all p: Post | one u: User, d: Discussion | p.status = PENDING implies
(d.discussionUid = p.discussionId and u.userUid = p.creatorId and (u.policyMakerId = none)) else p.status = ACCEPTED and
(d.discussionUid = p.discussionId and u.userUid = p.creatorId and (u.policyMakerId != none))
}
check publishAPost for 5


// G_i: Allow a Policy maker to accept a Post
assert confirmAPost {
	no p: Post | one d: Discussion | p.status = ACCEPTED and p.visibility = Invisible and p.discussionId = d.discussionUid
}
check confirmAPost for 5

// G_i: Allow a Policy maker to create a Discussion
assert createADiscussion {
	
}
check createADiscussion for 5
*/
-----------------------------------------------------------------------------------------------------------------
//Predicates

pred forum {	
	# Administrator = 1
	# DataType = 0
	# User = 4
	# Topic = 2
	# PolicyMaker = 2
	# Discussion = 3
	# Post = 7
	//# Email = 6
}
run forum for 10








































