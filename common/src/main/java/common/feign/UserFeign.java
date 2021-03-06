package common.feign;

import common.questionAnswer.Question;
import common.user.Follow;
import common.user.Letter;
import common.user.Ticket;
import common.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", url = "http://127.0.0.1:8081")
public interface UserFeign {
    //根据用户id查询用户
    @GetMapping("/api/user/{uid}")
    User getUser(@PathVariable("uid") int userId);

    //根据用户id查询用户的关注
    @GetMapping("/api/user/following-user/{id}")
    List<User> getFollowUser(@PathVariable("id") int userId);

    //根据用户id获取未读的站内信
    @GetMapping(value = "/api/letter/{uid}")
    List<Letter> getLetter(@PathVariable("uid") int uid);

    //用户登录
    @PostMapping("/api/user/login")
    Ticket login(@RequestParam("account") String account, @RequestParam("password") String password);

    //用户注册
    @PostMapping("api/user/reg")
    String reg(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("mailbox") String mailbox,
               @RequestParam("activeCode") String activeCode);

    //判断用户和实体之间是否关注
    @GetMapping(value = "/api/follow")
    Follow isFollow(@RequestParam("userId") int userId, @RequestParam("entityId") int entityId, @RequestParam("type") String type);

    //通过ticketId查询用户
    @GetMapping("api/user/ticket/{tid}")
    User getUserByTicketId(@PathVariable("tid") int ticketId);

    //发送站内信
    @PostMapping("/api/letter/send")
    String sendLetter(int senderId, @RequestParam("receiver") String userName, @RequestParam("content") String content);

    //获取用户关注的问题
    @GetMapping("/api/user/following-question/{id}/")
    List<Question> getFollowQuestion(@PathVariable("id") int userId);

    //更新用户信息
    @PostMapping("/api/user/edit")
    String editUser(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "mailbox", required = false) String mailbox,
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "userId", required = false) int userId,
            @RequestParam(value = "headUrl", required = false) String headUrl);

    //更换头像
    @PostMapping("/api/user/headUrl")
    String updateUserHeadUrl(@RequestParam("headUrl") String headUrl, @RequestParam("userId") String userId);
}
