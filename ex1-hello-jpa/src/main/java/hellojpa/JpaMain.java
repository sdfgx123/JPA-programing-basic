package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // DB transaction을 시작함

        // em은 정말 쉽게 말해서, DB connection 하나 받았다고 생각하면 됨
        // 이게 정석 코드 > try catch 문에 트랜잭션을 집어넣음
        try {
            /*Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);*/

            /*Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());
            */

            /*Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");*/

            // JPQL로 쿼리를 직접 날려서 List로 Member 객체 리스트 가져옴
            /*List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();
            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }*/

            /*// 비영속
            Member member = new Member();
            member.setId(160L);
            member.setName("B");
            // 영속
            System.out.println("===== BEFORE =====");
            em.persist(member);
            System.out.println("===== AFTER =====");*/

            /*Member member = em.find(Member.class, 150L);
            member.setName("zzzzzz");
            System.out.println("--------------------");*/

            /*Member member = new Member(200L, "member200");
            em.persist(member);
            // flush를 통해 DB에 쿼리가 즉시 나가게 됨
            em.flush();
            System.out.println("---------------------");*/

            /*Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");
            // detach 메서드를 통해 member 객체를 영속성 컨텍스트에서 제외함 >  더 이상 commit이 일어나지 않음
            //em.detach(member);
            // clear 하면 영속성 컨텍스트를 통으로 다 날려버림, 즉 1차 캐시를 초기화함
            em.clear();
            Member member2 = em.find(Member.class, 150L);
            System.out.println("----------------");*/

            /*Member member = new Member();
            member.setUsername("C");

            em.persist(member);*/

            /*
            엔티티 매니저 팩토리는 하나만 생성 후 앱 전체에서 공유
            엔티티 매니저는 쓰레드 간에 공유 X > 사용하고 버려야 함
            JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
             */

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
//            member.setTeamId(team.getId());
            em.persist(member);

            em.flush(); // 영속성 컨텍스트에 있는 것들 다 DB에 쿼리 날림 > 싱크 맞춤
            em.clear(); // 영속성 컨텍스트 초기화

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            /*// 객체지향의 지향성과 맞지 않음, 연관관계가 없기 때문임
            Member findMember = em.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId);
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());*/

            tx.commit(); // commit해서 반영
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
