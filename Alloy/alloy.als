//Signature

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
	policyMakerId: one Int,
}
{
	policyMakerId > 0
}

sig Post {
	text: one Text,
	creatorId: one Uid,
	timestamp: one DateTime,
	discussionId: one Uid,
	attachment: lone Object,
	status: one Status,
	postUid: one Uid,
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

-----------------------------------------------------------------------------------------------------------------
//Facts

fact{ //Each policyMakerId is unique
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

fact { //If a Post has a postId greater than another Post, then its timestamp is greater or equal respect to the other Discussion
	all p1, p2: Post | (p1.postUid > p2.postUid and not p1.timestamp < p2.timestamp)
}

fact { //If a Discussion has a postId greater than another Discussion, then its timestamp is greater or equal respect to the other Discussion
	all d1, d2: Discussion | (d1.discussionUid > d2.discussionUid and not d1.timestamp < d2.timestamp)
}

fact { //If a Topic has a postId greater than another Topic, then its timestamp is greater or equal respect to the other Topic
	all t1, t2: Topic | (t1.topicUid > t2.topicUid and not t1.timestamp < t2.timestamp)
}

fact { //Two Users can not be creators of the same Post
	all p: Post | (no disj u1, u2: User | (p.creatorId in u1.userUid and p.creatorId in u2.userUid))
}

fact { //Two Policy makers can not be creators of the same Discussion
	all d: Discussion | (no disj p1, p2: PolicyMaker | (d.creatorId in p1.userUid and d.creatorId in p2.userUid))
}










