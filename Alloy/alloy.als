//Signatures

sig User {
	name: one Name,
	surname: one Surname,
	email: one Email,
	dateOfBirth: one Date,
	areaOfResidence: one AreaOfResidence,
	userUid: one Uid,
}

sig Name {}

sig Surname {}

sig Email {}

sig Date {}

sig AreaOfResidence {}

sig Uid in Int {}
{
	Uid > 0
}

sig PolicyMaker extends User {
	policyMakerId: one Text,
}

sig Post {
	text: one Text,
	creatorId: one Uid,
	timestamp: one DateTime,
	discussionId: one Uid,
	attachment: lone Object,
	status: one Status,
	postUid: one Uid,
	visibility: one Visibility,
}

sig Text {}

sig DateTime in Int {}

abstract sig Status {}

sig PENDING extends Status{}

sig ACCEPTED extends Status {}

sig REJECTED extends Status {}

sig Object {}

sig Discussion {
	title: one Title,
	text: one Text,
	topicId: one Uid,
	timestamp: one DateTime,
	creatorId: one Uid,
	discussionUid: one Uid,
}

sig Title {}

sig Topic {
	title: one Title,
	timestamp: one DateTime,
	topicUid: one Uid,
}

sig Administrator {
	email: one Email,
	password: one Password,
	administratorUid: one Uid,
}

sig Password {}
{ //Each password is associated to an Administrator
	all p : Password | (some a: Administrator | a. password = p)
}

sig DataSource {
	name: one Name,
	source: one Source,
	description: lone Text,
	dataType: one DataType,
}

sig Source {}

sig DataType {
	name: one Name,
	dataTypeUid: one Uid,
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

fact { //Each Policy maker has an unique email
	no disj p1, p2 : PolicyMaker | p1.email = p2.email
}

fact { //Each Administrator has an unique email
	no disj a1, a2 : Administrator | a1.email = a2.email
}

fact { //If a post exist, it must be PENDING, ACCEPTED or REJECTED
	all p: Post | p.status = PENDING or p.status = ACCEPTED or p.status = REJECTED
}

fact { //There can not be two Posts with the same postId
	no disj p1, p2: Post | p1.postUid = p2.postUid
}

fact { //There can not be two Discussions with the same discussionId
	no disj d1, d2: Discussion | d1.discussionUid = d2.discussionUid
}

fact { //There can not be two Topics with the same topicId
	no disj t1, t2: Topic | t1.topicUid = t2.topicUid
}

fact { //If a Post has a postId greater than another Post, then its timestamp is greater or equal respect to the other Post
	all p1, p2: Post | (p1.postUid > p2.postUid and not p1.timestamp < p2.timestamp)
}

fact { //If a Discussion has a discussionId greater than another Discussion, then its timestamp is greater or equal respect to the other Discussion
	all d1, d2: Discussion | (d1.discussionUid > d2.discussionUid and not d1.timestamp < d2.timestamp)
}

fact { //If a Topic has a topicId greater than another Topic, then its timestamp is greater or equal respect to the other Topic
	all t1, t2: Topic | (t1.topicUid > t2.topicUid and not t1.timestamp < t2.timestamp)
}

fact { //Two Users can not be creators of the same Post
	all p: Post | (no disj u1, u2: User | (p.creatorId in u1.userUid and p.creatorId in u2.userUid))
}

fact { //Two Policy makers can not be creators of the same Discussion
	all d: Discussion | (no disj p1, p2: PolicyMaker | (d.creatorId in p1.userUid and d.creatorId in p2.userUid))
}

fact { //A Post always belong to one Discussion
	all p: Post | one d: Discussion | p.discussionId = d.discussionUid
}

fact { //A Discussion always belong to one Topic
	all d: Discussion | one t: Topic | d.topicId = t.topicUid
}

fact { //A Discussion has always a title
	all d: Discussion | one t: Title | d.title = t
}

fact { //A Topic has always a title
	all to: Topic | one ti: Title | to.title = ti
}

fact { //A Post can not exists without a creator
	all p: Post | one u: User | p.creatorId = u.userUid
}

fact { //A Discussion can not exists without a creator
	all d: Discussion | one p: PolicyMaker | d.creatorId = p.userUid
}

fact { //A Discussion can be created only by a Policy maker
	all d: Discussion | all p: PolicyMaker | d.creatorId = p.userUid and not p.policyMakerId = none
}

fact { //Each User has an unique userUid
	no disj u1, u2 : User | u1.userUid = u2.userUid
}

fact { //Each Policy maker has an unique userUid
	no disj p1, p2 : PolicyMaker | p1.userUid = p2.userUid
}

fact { //Each Administrator has an unique administratorUid
	no disj a1, a2 : Administrator | a1.administratorUid = a2.administratorUid
}

fact { //There can not exist a Post without Text and without Attachment
	no disj p: Post | p.text = none and p.attachment = none
}

fact { //A Data source can not exist without a source
	all d: DataSource | one s: Source | d.source = s
}

fact { //A Topic can contain more than one Discussion
	all t: Topic | some d: Discussion | d.topicId = t.topicUid
}

fact { //A Discussion can contain more than one Post
	all d: Discussion | some p: Post | d.discussionUid = p.discussionId
}

fact { //An User could have created more than one Post
	all u: User | some p: Post | u.userUid = p.creatorId
}

fact { //A Policy maker could have created more than one Discussion
	all p: PolicyMaker | some d: Discussion | p.userUid = d.creatorId
}

fact { //A Post is not visible if it has been rejected or is still in the pending list
	all p: Post | (p.status = PENDING or p.status = REJECTED) implies p.visibility = Invisible
}

fact { //A Post is visible if it has been accepted
	all p: Post | p.status = ACCEPTED implies p.visibility = Visible
}

-----------------------------------------------------------------------------------------------------------------
//Assertions

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

-----------------------------------------------------------------------------------------------------------------
//Predicates

pred world1 {
	# User = 4
	# PolicyMaker = 1
	# Administrator = 1
	# Email = 6
	# Password = 1
}
run world1








































