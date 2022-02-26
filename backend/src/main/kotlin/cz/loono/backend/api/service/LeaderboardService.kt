package cz.loono.backend.api.service

import cz.loono.backend.api.dto.LeaderboardDto
import cz.loono.backend.api.dto.LeaderboardUserDto
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.repository.AccountRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class LeaderboardService(
    private val accountRepository: AccountRepository
) {

    fun getLeaderboard(uid: String): LeaderboardDto {
        val top3 = findTop3Accounts(uid)
        val currentAcc = accountRepository.findByUid(uid)
        val myOrder = currentAcc?.points?.let { accountRepository.findMyPosition(currentAcc.points) + 1 } ?: 0
        val peers = accountRepository.findPeers(currentAcc?.points ?: 0).map { prepareLeaderboardUser(uid, it) }

        return LeaderboardDto(
            top = top3,
            peers = peers,
            myOrder = myOrder
        )
    }

    private fun findTop3Accounts(uid: String) = accountRepository.findAllByOrderByPointsDesc(PageRequest.of(0, 3)).map {
        prepareLeaderboardUser(uid, it)
    }

    private fun prepareLeaderboardUser(uid: String, account: Account): LeaderboardUserDto = LeaderboardUserDto(
        name = account.nickname,
        points = account.points,
        profileImageUrl = account.profileImageUrl,
        isThisMe = uid == account.uid
    )
}
