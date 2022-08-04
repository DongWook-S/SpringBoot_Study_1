package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        // D.I (Dependency Injection)
        // 클래스 밖에서 해당 클래스의 객체를 만들어 주는것
        // 'A는 B를 의존한다' 를 'A는 B한테 객체를 빌려온다
        // A 클래스에서 B 클래스 객체를 생성하고 사용하는 것
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given - 무언가가 주어졌는데
        Member member = new Member();
        member.setName("spring");

        // when - 이거를 실행 했을때
        Long saveId = memberService.join(member);

        // then - 결과가 이렇게 나와야한다.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalAccessException e = assertThrows(IllegalAccessException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.TEST");
        }
*/

        // then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}