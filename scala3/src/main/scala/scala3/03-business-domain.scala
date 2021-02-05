package scala3.domain

object DefinitionOne:
  final case class Username private (value: String)

  object Username:

    // apply is there, but private, as expected!
    def fromString(v: String): Option[Username] =
      if (v.matches("^@[a-z0-9]{4,32}$")) Some(Username.apply(v)) else None

    // copy is there, but private, as expected!
    def upper(username: Username): Username =
      username.copy(value = username.value.toUpperCase.nn)

// -----------------------------------------------------

@main
def exampleA =
  import DefinitionOne._
  val userA = Username.fromString("foo")
  val userB = Username.fromString("@foobar")

  println(s"userA = $userA")
  println(s"userB = $userB")

  // `apply` and `copy` are not available anymore - as expected
  // val userC = Username.apply("foo")
  // val userD = userB.get.copy(value = "foo")

// -----------------------------------------------------

object ExampleB:
  
  import java.util.UUID
  import scala.util.Try

  opaque type Email = String
  object Email:
    def fromString(v: String): Option[Email] =
      if isValidEmail(v) then Some(v) else None

    def isValidEmail(v: String): Boolean =
      v.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")

  opaque type UserId = UUID
  object UserId:
    def fromString(v: String): Option[UserId] =
      Try(UUID.fromString(v).nn).toOption

    def makeRandom: UserId = UUID.randomUUID.nn

  enum UserCommand:
    case SignUp(email: Email)
    case AddFriend(id: UserId, friendId: UserId)
    case RemoveFriend(id: UserId, friendId: UserId)

  enum UserEvent(userId: UserId):
    case UserCreated(userId: UserId, email: Email)       extends UserEvent(userId)
    case FriendAdded(userId: UserId, friendId: UserId)   extends UserEvent(userId)
    case FriendRemoved(userId: UserId, friendId: UserId) extends UserEvent(userId)

// -----------------------------------------------------

object TestExampleB:
  import ExampleB.UserCommand.SignUp
  import ExampleB.Email
  //val event1 = SignUp("foo")

  def email: Email = ???
  val event2 = SignUp(email)

// -----------------------------------------------------
