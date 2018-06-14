package ru.ifmo.se.Karlsson.Lab6;

import ru.ifmo.se.Karlsson.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimationManager {
    private static byte DIRECTION_X = 1;
    private static byte DIRECTION_Y = 2;

    private ScheduledExecutorService executorService;

    private List<Component> componentList;
    private Map<Component, Point> lastPoints;

    private byte animationDirection = DIRECTION_Y;
    private boolean active = false;


    public AnimationManager() {
        this(new ArrayList<>());
    }

    public AnimationManager(List<? extends Component> components) {
//        this.componentList = new CopyOnWriteArrayList<>();
        this.componentList = Collections.synchronizedList(new ArrayList<>());
        this.componentList.addAll(components);

        executorService = Executors.newScheduledThreadPool(4);

        savePositions();
    }

    public void addComponent(Component component) throws InterruptedException {
        if (!executorService.isShutdown()) {
            throw new InterruptedException();
        }

        componentList.add(component);
        lastPoints.put(component, component.getLocationOnScreen());
    }

    public void setDirection(byte direction) {
        this.animationDirection = direction;
    }

    public void startWithFixedDelay(final int initialDelay, final int delay, final int amount, final int step, final int growthCoefficient, final TimeUnit unit) {
        active = true;
        ClientGUI.start.setText(ClientGUI.stopText);
        componentList.forEach(comp -> {
            AtomicInteger counter = new AtomicInteger(0);

            executorService.scheduleWithFixedDelay(() ->
                    {
                        if (counter.get() >= amount || !active) {
                            return;
                        }

                        comp.setLocation(comp.getX() + (animationDirection == DIRECTION_X ? growthCoefficient * step : 0), comp.getY() + (animationDirection == DIRECTION_Y ? growthCoefficient * step : 0));

                        counter.getAndIncrement();
                    }
                    , initialDelay, delay, unit);
        });
        executorService.schedule(() -> {
            ClientGUI.start.setText(ClientGUI.startText);
        }, 7, TimeUnit.SECONDS);
    }

    public void stopAnimation() {
        if (isActive()) {
            active = false;
            executorService.shutdownNow();
            for ( Map.Entry<Component, Point> pair : lastPoints.entrySet() ) {
                pair.getKey().setLocation( pair.getValue() );
            }
        }
    }

    private void savePositions() {
        lastPoints = new ConcurrentHashMap<>(componentList.size());

        componentList.forEach(comp -> lastPoints.put(comp, new Point(comp.getX(), comp.getY())));
    }

    public boolean isActive() {
        return active && !executorService.isShutdown();
    }
}