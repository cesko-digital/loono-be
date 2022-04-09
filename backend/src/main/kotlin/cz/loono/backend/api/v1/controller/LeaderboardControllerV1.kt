package cz.loono.backend.api.v1.controller

import cz.loono.backend.api.Attributes
import cz.loono.backend.api.BasicUser
import cz.loono.backend.api.dto.LeaderboardDto
import cz.loono.backend.api.v1.service.LeaderboardServiceV1
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/leaderboard", produces = [MediaType.APPLICATION_JSON_VALUE])
class LeaderboardControllerV1(
    private val leaderboardService: LeaderboardServiceV1
) {

    @GetMapping
    fun getLeaderboard(
        @RequestAttribute(name = Attributes.ATTR_BASIC_USER)
        basicUser: BasicUser,
        @RequestParam(name = "leaderboard_size")
        leaderboardSize: Int = 100
    ): LeaderboardDto = leaderboardService.getLeaderboard(basicUser.uid)
}
