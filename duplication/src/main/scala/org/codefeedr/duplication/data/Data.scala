package org.codefeedr.duplication.data

import java.util.Date

object Data {

  case class _id(
      `$oid`: String
  )

  case class Actor(
      id: Double,
      login: String,
      display_login: String,
      gravatar_id: String,
      url: String,
      avatar_url: String
  )

  case class Repo(
      id: Double,
      name: String,
      url: String
  )

  case class Author(
      email: String,
      name: String
  )

  case class Commits(
      sha: String,
      author: Author,
      message: String,
      distinct: Boolean,
      url: String
  )

  case class Payload(
      push_id: Double,
      size: Double,
      distinct_size: Double,
      ref: String,
      head: String,
      before: String,
      commits: List[Commits]
  )

  case class Org(
      id: Double,
      login: String,
      gravatar_id: String,
      url: String,
      avatar_url: String
  )

  case class PushEvent(
      _id: _id,
      id: String,
      `type`: String,
      actor: Actor,
      repo: Repo,
      payload: Payload,
      public: Boolean,
      created_at: Date,
      org: Option[Org]
  )

}
