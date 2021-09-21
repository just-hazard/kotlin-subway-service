package nextstep.subway.member.ui

import nextstep.subway.auth.domain.LoginMember
import nextstep.subway.member.application.MemberService
import nextstep.subway.member.dto.MemberRequest
import nextstep.subway.member.dto.MemberResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class MemberController(private val memberService: MemberService) {
    @PostMapping("/members")
    fun createMember(@RequestBody request: MemberRequest?): ResponseEntity<*> {
        val member = memberService.createMember(request!!)
        return ResponseEntity.created(URI.create("/members/" + member.id)).build<Any>()
    }

    @GetMapping("/members/{id}")
    fun findMember(@PathVariable id: Long?): ResponseEntity<MemberResponse> {
        val member = memberService.findMember(id!!)
        return ResponseEntity.ok().body(member)
    }

    @PutMapping("/members/{id}")
    fun updateMember(@PathVariable id: Long?, @RequestBody param: MemberRequest?): ResponseEntity<MemberResponse> {
        memberService.updateMember(id!!, param!!)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/members/{id}")
    fun deleteMember(@PathVariable id: Long?): ResponseEntity<MemberResponse> {
        memberService.deleteMember(id!!)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/members/me")
    fun findMemberOfMine(loginMember: LoginMember): ResponseEntity<MemberResponse> {
        val member = memberService.findMember(loginMember.id!!)
        return ResponseEntity.ok().body(member)
    }

    @PutMapping("/members/me")
    fun updateMemberOfMine(
        loginMember: LoginMember,
        @RequestBody param: MemberRequest?
    ): ResponseEntity<MemberResponse> {
        memberService.updateMember(loginMember.id!!, param!!)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/members/me")
    fun deleteMemberOfMine(loginMember: LoginMember): ResponseEntity<MemberResponse> {
        memberService.deleteMember(loginMember.id!!)
        return ResponseEntity.noContent().build()
    }
}
