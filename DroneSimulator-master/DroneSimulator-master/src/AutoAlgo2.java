/*
    public class AutoAlgo2 {

    }

    import java.util.EnumMap;
            import java.util.EnumSet;
            import java.util.Map;

    enum State {
        HOVER(1),
        RIGHT_WALL(2),
        TWO_WALL_FLIGHT(3),
        UP(4),
        DOWN(5),
        SCAN(6),
        EMERGENCY(0);

        private final int value;

        State(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public class DroneController {

        private static final double DIST_RIGHT = 0.5;
        private static final double DIST_LEFT = 1.0;
        private static final double DIST_FRONT = 0.5;
        private static final double DIST_RIGHT_EMERGENCY = 0.4;
        private static final double DIST_LEFT_EMERGENCY = 0.2;
        private static final double DIST_FRONT_EMERGENCY = 0.2;
        private static final double DIST_BACK = 0.5;

        private static final int PITCH_SPEED = 10;
        private static final int ROLL_SPEED = 5;
        private static final int YAW_SPEED = 60;
        private static final int PITCH_SPEED_EMERGENCY = -5;
        private static final int ROLL_SPEED_EMERGENCY = 5;
        private static final int TURN_BY_YAW = 60;
        private static final int LIDAR_INFINITY = 4;
        private static final double RIGHT_ERROR = 0.5;
        private static final int YAW_PERMANENT = -5;

        private static final double PR = 10;
        private static final double IR = 0;
        private static final double DR = 15;

        private static final double PL = 1;
        private static final double IL = 0.000001;
        private static final double DL = 6;

        private static final double PF = 0.7;
        private static final double IF = 0;
        private static final double DF = 1;

        private static int rollHistory = 0;
        private static int pitchHistory = 10;
        private static int yawRateHistory = 0;
        private static int zHistory = -1;

        private static PidController rightPid = new PidController(PR, IR, DR, DIST_RIGHT);
        private static PidController frontPid = new PidController(PF, IF, DF, DIST_FRONT);
        private static PidController leftPid = new PidController(PL, IL, DL, DIST_LEFT);

        static {
            rightPid.setBounds(-30, 10);
            frontPid.setBounds(-30, 8);
            leftPid.setBounds(-30, 10);
        }

        private static void normalizeLidars(Map<String, Double> lidars) {
            if (lidars.get("front") < 0) {
                System.out.println("Front normalized");
                lidars.put("front", DIST_FRONT);
            }
            if (lidars.get("right") < 0) {
                System.out.println("Right normalized");
                lidars.put("right", DIST_RIGHT);
            }
        }

        public static void loop(Drone drone, GUIManager gui) {
            while (true) {
                Map<String, Double> lidars = drone.getLidars();
                Map<String, Double> position = drone.getPosition();
                Map<String, Double> orientation = drone.getOrientation();
                Map<String, Double> velocity = drone.getVelocity();
                normalizeLidars(lidars);
                System.out.println(lidars);

                double roll = 0;
                double pitch = 0;
                double yawRate = 0;
                double z = 0;

                long nowTime = System.currentTimeMillis();
                double pitchAns = frontPid.pid(lidars.get("front"), nowTime);
                double rollAns = rightPid.pid(lidars.get("right"), nowTime);

                StringBuilder infoStr = new StringBuilder();

                roll = rollAns;
                pitch = pitchAns;
                yawRate = YAW_PERMANENT;
                z = -1;

                rollHistory = (int) roll;
                pitchHistory = (int) pitch;
                yawRateHistory = (int) yawRate;
                zHistory = -1;

                infoStr.append("\n");

                if (lidars.get("front") > 0 && lidars.get("front") < DIST_FRONT * 3) {
                    roll = rollAns;
                    pitch = pitchAns;
                    yawRate = YAW_SPEED * 3;
                    z = -1;
                    System.out.println("else front: " + pitch);
                    infoStr.append(String.format("Front rotate: %.2f%n", pitch));
                }

                if (lidars.get("right") > LIDAR_INFINITY || lidars.get("right").equals(RIGHT_ERROR)) {
                    infoStr.append(String.format("Right rotate: %.2f%n", pitch));
                    drone.turnBy(0, 0, TURN_BY_YAW, true);
                    lidars = drone.getLidars();
                    if (lidars.get("front") >= DIST_FRONT) {
                        drone.moveBy(1, 0, 0, true);
                    }
                }

                if (velocity.get("x") > 0.3) {
                    pitch = -3;
                }

                drone.command(roll, pitch, yawRate, -1);

                infoStr.append(String.format("Roll: %.2f\tPitch: %.2f\tYaw: %.2f%n", roll, pitch, yawRate));
                gui.setAlgoInfo(infoStr.toString());

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            try (Manager man = new Manager(coordinate_system.AIRSIM, DroneController::loop);
                 GUIManager gui = new GUIManager(man, 10, 10, 10, 3)) {
                gui.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/
