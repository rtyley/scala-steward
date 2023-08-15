/*
 * Copyright 2018-2023 Scala Steward contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scalasteward.core.nurture

import cats.Order
import org.http4s.Uri

/** A URL of a resource that provides additional information for an update. */
case class UpdateInfoUrl(typ: UpdateInfoUrlType, url: Uri) {
  lazy val asMarkdown: String = s"[${typ.name}](${url.renderString})"
}

sealed class UpdateInfoUrlType(val name: String)

object UpdateInfoUrl {
  object CustomChangelog extends UpdateInfoUrlType("Changelog")
  object CustomReleaseNotes extends UpdateInfoUrlType("Release Notes")
  object GitHubReleaseNotes extends UpdateInfoUrlType("GitHub Release Notes")
  object VersionDiff extends UpdateInfoUrlType("Version Diff")

  val All: Seq[UpdateInfoUrlType] =
    Seq(GitHubReleaseNotes, CustomReleaseNotes, CustomChangelog, VersionDiff)

  implicit val updateInfoUrlTypeOrder: Order[UpdateInfoUrlType] = Order.by(All.indexOf)

  implicit val updateInfoUrlOrder: Order[UpdateInfoUrl] = Order.by(uiu => (uiu.typ, uiu.url))
}
